package union.seosan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import union.seosan.dto.barcode.DeliveryCardInputDto;
import union.seosan.entity.DeliveryCard;
import union.seosan.entity.DeliveryPartCard;
import union.seosan.entity.Part;
import union.seosan.repository.DeliveryCardRepository;
import union.seosan.repository.DeliveryPartCardRepository;

import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
@Service
public class BarcodeService {

    private final DeliveryCardRepository dcRepo;
    private final DeliveryPartCardRepository pcRepo;

    private void createDCAndPC(DeliveryCardInputDto inputDto) {
        AtomicBoolean confirmDifference  = new AtomicBoolean(false);

        DeliveryCard deliveryCard = DeliveryCard.builder()
                .deliveryBarcode(inputDto.getDeliveryBarcode())
                .build();
        dcRepo.save(deliveryCard);

        inputDto.getDeliveryParts().forEach(dcDto -> {
            DeliveryPartCard deliveryPartCard = DeliveryPartCard.builder()
                    .partBarcode(dcDto.getPartBarcode())
                    .part(Part.builder()
                            .partCode(dcDto.getPartCode())
                            .build())
                    .lot(dcDto.getLot())
                    .cardStock(dcDto.getCardStock())
                    .stock(dcDto.getStock())
                    .build();
            if(dcDto.getStock() != dcDto.getCardStock()) {
                deliveryPartCard.confirmDifference();
                confirmDifference.set(true);
            }
            pcRepo.save(deliveryPartCard);
        });

        if(confirmDifference.get()){
            deliveryCard.confirmDifference();
            dcRepo.save(deliveryCard);
        }

    }
}
