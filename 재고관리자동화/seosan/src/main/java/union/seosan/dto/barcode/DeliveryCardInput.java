package union.seosan.dto.barcode;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeliveryCardInput {
    private String deliveryBarcode;
    private List<DeliveryPartCardInput> deliveryParts = new ArrayList<>();
}
