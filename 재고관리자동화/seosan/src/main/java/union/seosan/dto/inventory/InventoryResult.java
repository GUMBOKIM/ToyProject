package union.seosan.dto.inventory;

import lombok.Data;

@Data
public class InventoryResult {
    private String partCode;
    private String lot;
    private int amount;
    private int beforeStock;
    private int afterStock;
    private String result = "Y";

    public InventoryResult(String partCode, String lot, int amount) {
        this.partCode = partCode;
        this.lot = lot;
        this.amount = amount;
    }

}
