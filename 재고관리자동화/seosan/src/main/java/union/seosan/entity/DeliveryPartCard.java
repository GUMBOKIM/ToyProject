package union.seosan.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import union.seosan.dto.barcode.DeliveryPartCardInput;

import javax.persistence.*;

// 부품 바코드
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeliveryPartCard extends BaseTimeEntity {

    @Id
    @Column
    private String partBarcode;

    @ManyToOne
    @JoinColumn(name = "deliveryBarcode")
    private DeliveryCard deliveryCard;

    @ManyToOne
    @JoinColumn(name = "partCode")
    private Part part;

    @Column(nullable = false)
    private String lot;

    @Column(nullable = false)
    private int cardAmount;

    @Column(nullable = false)
    private int amount;

    @Column
    private String differenceYn = "Y";

    public void confirmDifference() {
        this.differenceYn = "N";
    }

    public void setDeliveryCard(DeliveryCard deliveryCard) {
        this.deliveryCard = deliveryCard;
    }

    public DeliveryPartCard(DeliveryPartCardInput dcDto) {
        this.partBarcode = dcDto.getPartBarcode();
        this.part = Part.builder().partCode(dcDto.getPartCode()).build();
        this.lot = dcDto.getLot();
        this.cardAmount = dcDto.getCardAmount();
        this.amount = dcDto.getAmount();
    }
}
