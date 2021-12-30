package union.seosan.dto.barcode;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeliveryCardInputDto {
    private String deliveryBarcode;
    private List<DeliveryPartCardInputDto> deliveryParts = new ArrayList<>();
}
