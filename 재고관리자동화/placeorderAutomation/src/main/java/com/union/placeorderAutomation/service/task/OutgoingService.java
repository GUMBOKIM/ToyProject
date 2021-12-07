package com.union.placeorderAutomation.service.task;

import com.union.placeorderAutomation.dto.common.OrderHistoryDto;
import com.union.placeorderAutomation.dto.resttemplate.CreateDeliveryDto;
import com.union.placeorderAutomation.dto.resttemplate.PartInventoryDto;
import com.union.placeorderAutomation.dto.resttemplate.ProductPlanDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingPartDto;
import com.union.placeorderAutomation.dto.task.outgoing.OutgoingSubmitDto;
import com.union.placeorderAutomation.entity.*;
import com.union.placeorderAutomation.repository.BomRepository;
import com.union.placeorderAutomation.repository.PartInventoryRepository;
import com.union.placeorderAutomation.repository.PartRepository;
import com.union.placeorderAutomation.service.common.CommonService;
import com.union.placeorderAutomation.service.common.PartLogService;
import com.union.placeorderAutomation.service.resttemplate.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class OutgoingService {

    private final RestTemplateService restTemplateService;
    private final CommonService commonService;
    private final PartLogService partLogService;
    private final BomRepository bomRepo;
    private final PartRepository partRepo;
    private final PartInventoryRepository partInventoryRepo;

    @Transactional(readOnly = true)
    public int findPartStock(String bwCode) {
        Long partStock = partInventoryRepo.sumPartStock(bwCode);
        if (partStock == null) {
            return 0;
        } else {
            return partStock.intValue();
        }
    }

    //일반 납품
    @Transactional(readOnly = true)
    public List<ProductPlanDto> findPlanAndInventory(String companyCode, String plantCode) {
        // 재고 찾기(공장) -> 회사 전체 부품으로 걸러냄
        HashMap<String, PartInventoryDto> partInvenMap = restTemplateService.getPartInventory(companyCode, plantCode);
        List<Part> findPartList = partRepo.findPartByCompany(companyCode);
        HashMap<String, PartInventoryDto> partInvenResult = new HashMap<>();
        for (Part part : findPartList) {
            String bwCode = part.getBwCode();
            if (partInvenMap.containsKey(bwCode)) {
                PartInventoryDto partInventoryDto = partInvenMap.get(bwCode);
                partInventoryDto.setLoadAmount(part.getLoadAmount());
                partInventoryDto.setStock(findPartStock(part.getBwCode()));
                partInvenResult.put(bwCode, partInventoryDto);
            }
        }

        // 생산계획(BOM) 찾기(공장, 라인으로 검색)
        List<ProductPlanDto> planList = new ArrayList<>();
        planList.addAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CS"));
        planList.addAll(restTemplateService.getProductPlanning(companyCode, plantCode, "CP"));
        planList.addAll(restTemplateService.getProductPlanning(companyCode, plantCode, "MC"));
        // 회사 제품이 들어간 BOM 필터링
        List<Bom> findBomList = bomRepo.findByCompanyCode(Company.builder().companyCode(companyCode).build());
        HashMap<String, Bom> bomMap = new HashMap<>();
        for (Bom bom : findBomList) {
            bomMap.put(bom.getBwCode(), bom);
        }
        for (ProductPlanDto plan : planList) {
            String bomBwCode = plan.getBomBwCode();
            if (bomMap.containsKey(bomBwCode)) {
                List<BomPart> bomParts = bomMap.get(bomBwCode).getBomParts();
                List<PartInventoryDto> partInventory = new ArrayList<>();
                for (BomPart bomPart : bomParts) {
                    String partBwCode = bomPart.getPart().getBwCode();
                    int usage = bomPart.getAmount();

                    if (partInvenResult.containsKey(partBwCode)) {
                        PartInventoryDto partInventoryDto = partInvenResult.get(partBwCode);
                        partInventoryDto.setUsage(usage);
                        partInventory.add(partInventoryDto);
                    }
                }
                plan.setPartInventory(partInventory);
            }
        }
        return planList;
    }

    //선택품 찾기
    @Transactional(readOnly = true)
    public List<PartInventoryDto> findSelectPartInventory(String companyCode, String plantCode) {
        // 재고 찾기(공장) -> 회사 전체 부품으로 걸러냄
        HashMap<String, PartInventoryDto> partInvenMap = restTemplateService.getPartInventory(companyCode, plantCode);
        List<Part> findPartList = partRepo.findSelectPartByCompany(companyCode);
        List<PartInventoryDto> partInvenResult = new ArrayList<>();
        for (Part part : findPartList) {
            String bwCode = part.getBwCode();
            if (partInvenMap.containsKey(bwCode)) {
                PartInventoryDto partInventoryDto = partInvenMap.get(bwCode);
                partInventoryDto.setSpCode(part.getSpCode());
                partInventoryDto.setPartBwCode(part.getBwCode());
                partInventoryDto.setLoadAmount(part.getLoadAmount());
                partInventoryDto.setStock(findPartStock(part.getBwCode()));
                partInvenResult.add(partInventoryDto);
            }
        }
        return partInvenResult;
    }

    public List<CreateDeliveryDto> submitPart(OutgoingSubmitDto submitDto) {
        List<CreateDeliveryDto> deliveryList = createDeliveryCard(submitDto);
        submitDto.setPartList(null);
        restTemplateService.createDeliveryCard(submitDto, convertForCreateDeliverCard(deliveryList));
        log.info("납입카드 성공");
        restTemplateService.registryDelivery(submitDto, deliveryList);
        log.info("재고 등록 성공");
        partLogService.createOutcomeLogs(submitDto, deliveryList);
        log.info("로그 생성 성공");
        commonService.addCompanyOrderHistory(
                OrderHistoryDto.builder()
                        .orderSeq(submitDto.getOrderSeq())
                        .date(submitDto.getDate())
                        .time(submitDto.getTime())
                        .plantCode(submitDto.getPlantCode())
                        .companyCode(submitDto.getCompanyCode())
                        .build()
        );
        log.info("주문 기록 생성 성공");
        return deliveryList;
    }

    @Transactional(readOnly = true)
    public List<CreateDeliveryDto> checkPartList(OutgoingSubmitDto request) {
        return createDeliveryCard(request);
    }

    private List<CreateDeliveryDto> createDeliveryCard(OutgoingSubmitDto submitDto) {
        List<CreateDeliveryDto> result = new ArrayList<>();
        for (OutgoingPartDto part : submitDto.getPartList()) {
            int amount = part.getAmount();
            List<CreateDeliveryDto> temp = new ArrayList<>();
            // 총 수량 >= 납품 양
            if (amount <= partInventoryRepo.sumPartStock(part.getBwCode()).intValue()) {
                List<PartInventory> inventoryList = partInventoryRepo.findInventoryListByPart(part.getBwCode());
                Part p = partRepo.getById(part.getBwCode());
                for (PartInventory inventory : inventoryList) {
                    if (amount > inventory.getStock()) {
                        amount -= inventory.getStock();
                        temp.add(
                                CreateDeliveryDto
                                        .builder()
                                        .bwCode(p.getBwCode())
                                        .partName(p.getPartName())
                                        .inventoryBwCode(p.getInventoryBwCode())
                                        .poCode1(p.getPoCode1())
                                        .poCode2(p.getPoCode2())
                                        .quantity(inventory.getStock())
                                        .lot(inventory.getLot())
                                        .loadAmount(p.getLoadAmount())
                                        .location1(p.getLocation1())
                                        .location2(p.getLocation2())
                                        .build()
                        );
                        inventory.setStock(0);
                        partInventoryRepo.save(inventory);
                    } else {
                        temp.add(
                                CreateDeliveryDto
                                        .builder()
                                        .bwCode(p.getBwCode())
                                        .partName(p.getPartName())
                                        .inventoryBwCode(p.getInventoryBwCode())
                                        .poCode1(p.getPoCode1())
                                        .poCode2(p.getPoCode2())
                                        .lot(inventory.getLot())
                                        .quantity(amount)
                                        .loadAmount(p.getLoadAmount())
                                        .location1(p.getLocation1())
                                        .location2(p.getLocation2())
                                        .build()
                        );
                        inventory.setStock(inventory.getStock() - amount);
                        partInventoryRepo.save(inventory);
                        result.addAll(temp);
                        break;
                    }
                }
            }
        }
        return result;
    }


    // 추가
    private List<CreateDeliveryDto> convertForCreateDeliverCard(List<CreateDeliveryDto> input) {
        List<CreateDeliveryDto> result = new ArrayList<>();

        LinkedHashMap<String, List<CreateDeliveryDto>> dtoMap = new LinkedHashMap<>();

        for (CreateDeliveryDto dto : input) {
            String bwCode = dto.getBwCode();
            List<CreateDeliveryDto> deliveryDtos = new ArrayList<>();
            if (dtoMap.containsKey(bwCode)) {
                deliveryDtos = dtoMap.get(bwCode);
                deliveryDtos.add(dto);
                dtoMap.put(bwCode, deliveryDtos);
            } else {
                deliveryDtos.add(dto);
                dtoMap.put(bwCode, deliveryDtos);
            }
        }

        for (Map.Entry<String, List<CreateDeliveryDto>> mapList : dtoMap.entrySet()) {
            result.add(summaryCreateDeliveryCard(mapList.getValue()));
        }

        return result;
    }

    private CreateDeliveryDto summaryCreateDeliveryCard(List<CreateDeliveryDto> input) {
        CreateDeliveryDto origin = input.get(0);
        CreateDeliveryDto result = CreateDeliveryDto.builder()
                .bwCode(origin.getBwCode())
                .inventoryBwCode(origin.getInventoryBwCode())
                .partName(origin.getPartName())
                .poCode1(origin.getPoCode1())
                .poCode2(origin.getPoCode2())
                .loadAmount(origin.getLoadAmount())
                .location1(origin.getLocation1())
                .location2(origin.getLocation2())
                .build();
        List<String> lotList = new ArrayList<>();
        List<Integer> quantityList = new ArrayList<>();
        for (CreateDeliveryDto dto : input) {
            lotList.add(dto.getLot());
            quantityList.add(dto.getQuantity());
        }
        result.setLot(summaryLot(lotList));
        result.setQuantity(summaryQuantity(quantityList));
        return result;
    }

    private String summaryLot(List<String> lotList) {
        HashMap<String, HashMap<String, List<String>>> lotMap = new HashMap<>();
        for (String lot : lotList) {
            String[] lotArr = lot.split("");
            if (lotMap.containsKey(lotArr[0])) {
                HashMap<String, List<String>> secondMap = lotMap.get(lotArr[0]);
                if (secondMap.containsKey(lotArr[1])) {
                    List<String> thirdList = secondMap.get(lotArr[1]);
                    thirdList.add(lotArr[2]);
                    secondMap.put(lotArr[1], thirdList);
                } else {
                    List<String> thirdList = new ArrayList<>();
                    thirdList.add(lotArr[2]);
                    secondMap.put(lotArr[1], thirdList);
                }
                lotMap.put(lotArr[0], secondMap);
            } else {
                HashMap<String, List<String>> secondMap = new HashMap<>();
                List<String> thirdList = new ArrayList<>();
                thirdList.add(lotArr[2]);
                secondMap.put(lotArr[1], thirdList);
                lotMap.put(lotArr[0], secondMap);
            }
        }

        StringBuilder resultLot = new StringBuilder();
        for (Map.Entry<String, HashMap<String, List<String>>> secondEntry : lotMap.entrySet()) {
            String firstChar = secondEntry.getKey();
            resultLot.append(firstChar);
            HashMap<String, List<String>> secondMap = secondEntry.getValue();
            for (Map.Entry<String, List<String>> thirdEntry : secondMap.entrySet()) {
                String secondChar = thirdEntry.getKey();
                resultLot.append(secondChar);
                List<String> thirdCharList = thirdEntry.getValue();
                for (String thirdChar : thirdCharList) {
                    resultLot.append(thirdChar);
                    resultLot.append(".");
                }
                resultLot.deleteCharAt(resultLot.length() - 1);
                resultLot.append(".");
            }
            resultLot.deleteCharAt(resultLot.length() - 1);
            resultLot.append(".");
        }
        resultLot.deleteCharAt(resultLot.length() - 1);

        return resultLot.toString();
    }

    private int summaryQuantity(List<Integer> quantityList) {
        int result = 0;
        for (int quantity : quantityList) {
            result += quantity;
        }
        return result;
    }

}