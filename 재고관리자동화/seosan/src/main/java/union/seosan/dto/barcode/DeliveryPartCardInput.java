package union.seosan.dto.barcode;

import lombok.Data;

@Data
public class DeliveryPartCardInput {
    private String partBarcode;
    private String partCode;
    private String lot;
    private int cardAmount;
    private int amount;
}
