package union.seosan.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 재고
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Inventory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @ManyToOne
    @JoinColumn(name = "partCode")
    private Part part;

    @Column(nullable = false)
    private String lot;

    @Column(nullable = false)
    private int stock;

    @Builder
    public Inventory(Part part, String lot, int stock) {
        this.part = part;
        this.lot = lot;
        this.stock = stock;
    }

    public void increaseStock(int amount){
        this.stock += amount;
    }

    public void decreaseStock(int amount){
        this.stock -= amount;
    }
}
