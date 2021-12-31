package union.seosan.dto.inventory;

import lombok.Data;
import union.seosan.entity.Inventory;

@Data
public class InventoryDto {
    private String partCode;
    private String lot;
    private int stock;

    public InventoryDto(Inventory inventory) {
        this.partCode = inventory.getPart().getPartCode();
        this.lot = inventory.getLot();
        this.stock = inventory.getStock();
    }
}
