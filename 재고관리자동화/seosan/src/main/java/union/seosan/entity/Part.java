package union.seosan.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 부품
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Part extends BaseTimeEntity {

    @Id
    @Column
    private String partCode;

    @Column(nullable = false)
    private String partName;

    @Column(nullable = false)
    private int loadAmount;

    @OneToMany(mappedBy = "part", fetch = FetchType.LAZY)
    private List<Inventory> inventories = new ArrayList<>();

    @Builder
    public Part(String partCode, String partName, int loadAmount) {
        this.partCode = partCode;
        this.partName = partName;
        this.loadAmount = loadAmount;
    }
}
