package union.seosan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import union.seosan.dto.inventory.InventoryDto;
import union.seosan.dto.inventory.InventoryReflectResult;
import union.seosan.entity.Inventory;
import union.seosan.entity.Part;
import union.seosan.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepo;

    // 자재 전체 재고 확인
    private int findInventoryTotalStock(Part part){
        return inventoryRepo.findInventoryTotalStock(part);
    }

    // 부품 로트 - 재고 현황
    private List<InventoryDto> findInventory(Part part){
        List<InventoryDto> result = new ArrayList<>();
        for (Inventory inventory: inventoryRepo.findInventoriesByPart(part)) {
            result.add(new InventoryDto(inventory));
        }
        // CHECK : lamda를 꼭 써야하는지? inventoryRepo.findInventoriesByPart(part).stream().map(inventory -> result.add(new InventoryDto(inventory)));
        return result;
    }

    // 재고 입고
    // 1. 해당 로트 미존재   -> 새로 생성
    // 2, 해당 로트 존재    -> 기존 재고 수량에 추가
    private InventoryReflectResult increaseStock(Part part, String lot, int amount) {
        InventoryReflectResult result = new InventoryReflectResult(part.getPartCode(), lot, amount, "IN");
        Optional<Inventory> inventoryOpt = inventoryRepo.findInventoryByPartAndLot(part, lot);
        if (inventoryOpt.isEmpty()) {
            result.setBeforeStock(0);

            Inventory newInventory = Inventory.builder()
                    .part(part)
                    .lot(lot)
                    .stock(amount)
                    .build();
            inventoryRepo.save(newInventory);

            result.setBeforeStock(newInventory.getStock());
            result.setDesc("create lot");
        } else {
            Inventory originInventory = inventoryOpt.get();
            result.setBeforeStock(originInventory.getStock());

            originInventory.increaseStock(amount);
            inventoryRepo.save(originInventory);

            result.setBeforeStock(originInventory.getStock());
            result.setDesc("increase stock");
        }
        return result;
    }

    // 재고 출고
    // 1. 재고가 없는 경우
    // 2. 재고가 있는 경우
    //      1. 정상 출고
    //      2. 재고가 부족한 경우
    private InventoryReflectResult decreaseStock(Part part, String lot, int amount) {
        InventoryReflectResult result = new InventoryReflectResult(part.getPartCode(), lot, amount, "DE");

        Optional<Inventory> inventoryOpt = inventoryRepo.findInventoryByPartAndLot(part, lot);
        if (inventoryOpt.isEmpty()) {
            result.setBeforeStock(0);
            result.setAfterStock(0);
            result.setReflectYn("N");
            result.setDesc("not exist");
        } else {
            Inventory originInventory = inventoryOpt.get();
            result.setBeforeStock(originInventory.getStock());

            if(originInventory.getStock() >= amount){
                originInventory.decreaseStock(amount);
                inventoryRepo.save(originInventory);
                result.setDesc("decrease stock");
            } else {
                result.setReflectYn("N");
                result.setDesc("out of stock");
            }
            result.setAfterStock(originInventory.getStock());
        }
        return result;
    }
}
