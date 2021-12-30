package union.seosan.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 납입 카드
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeliveryCard extends BaseTimeEntity {

    @Id
    @Column
    private String deliveryBarcode;

    @Column
    private String differenceYn = "Y";

    @OneToMany(mappedBy = "deliveryCard", fetch = FetchType.LAZY)
    private List<DeliveryPartCard> deliveryPartCards = new ArrayList<>();

    public DeliveryCard(String deliveryBarcode) {
        this.deliveryBarcode = deliveryBarcode;
    }

    public void addDeliveryPartCard(DeliveryPartCard deliveryPartCard) {
        deliveryPartCard.setDeliveryCard(this);
        deliveryPartCards.add(deliveryPartCard);
    }

    public void confirmDifference() {
        this.differenceYn = "N";
    }
}
