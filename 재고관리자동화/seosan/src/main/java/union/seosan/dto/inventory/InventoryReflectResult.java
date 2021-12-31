package union.seosan.dto.inventory;

import lombok.Data;

@Data
public class InventoryReflectResult {
    private String partCode;
    private String lot;
    private int amount;
    private int beforeStock;
    private int afterStock;
    private String reflectYn = "Y";

    private String kind; // IN, DE - 재고 상승, 재고 감소
    private String desc; // 재고 반영 내용 - 새로 로트가 만들어 졌는지, 기존 로트에서 감소됬는지

    public InventoryReflectResult(String partCode, String lot, int amount, String kind) {
        this.partCode = partCode;
        this.lot = lot;
        this.amount = amount;
        this.kind = kind;
    }

}
