package union.seosan.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private int cardStock;

    @Column(nullable = false)
    private int stock;

    @ColumnDefault("Y")
    private String differenceYn;

    @Builder
    public DeliveryPartCard(String partBarcode, DeliveryCard deliveryCard, Part part, String lot, int cardStock, int stock) {
        this.partBarcode = partBarcode;
        this.deliveryCard = deliveryCard;
        this.part = part;
        this.lot = lot;
        this.cardStock = cardStock;
        this.stock = stock;
    }

    public void confirmDifference(){
        this.differenceYn = "N";
    }
}
