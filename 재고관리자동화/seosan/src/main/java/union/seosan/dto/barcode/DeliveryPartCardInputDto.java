package union.seosan.dto.barcode;

import lombok.Data;

@Data
public class DeliveryPartCardInputDto {
    private String partBarcode;
    private String partCode;
    private String lot;
    private int cardStock;
    private int stock;
}
