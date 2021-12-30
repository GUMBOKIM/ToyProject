package union.seosan.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import union.seosan.dto.barcode.DeliveryCardInput;
import union.seosan.dto.barcode.DeliveryPartCardInput;
import union.seosan.entity.DeliveryCard;
import union.seosan.entity.DeliveryPartCard;
import union.seosan.repository.DeliveryCardRepository;


@RequiredArgsConstructor
@Service
public class BarcodeService {

    private final DeliveryCardRepository dcRepo;

    private DeliveryCard createDCAndPC(DeliveryCardInput input) {
        DeliveryCard deliveryCard = new DeliveryCard(input.getDeliveryBarcode());
        for (DeliveryPartCardInput dcDto : input.getDeliveryParts()) {
            DeliveryPartCard deliveryPartCard = new DeliveryPartCard(dcDto);
            if (dcDto.getAmount() != dcDto.getCardAmount()) {
                deliveryPartCard.confirmDifference();
                deliveryCard.confirmDifference();
            }
            deliveryCard.addDeliveryPartCard(deliveryPartCard);
        }
        dcRepo.save(deliveryCard);
        return deliveryCard;
    }



}
