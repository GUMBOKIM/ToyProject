package union.seosan.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 재고
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PartInventory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @ManyToOne
    @JoinColumn(name = "partCode")
    private Part part;

    @Column(nullable = false)
    private String lot;

    @Column(nullable = false)
    private int amount;

}
