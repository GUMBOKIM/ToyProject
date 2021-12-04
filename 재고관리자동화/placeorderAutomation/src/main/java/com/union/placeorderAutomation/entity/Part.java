package com.union.placeorderAutomation.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Part {

    // 보그워너 코드
    @Id
    @Column(unique = true, nullable = false)
    private String bwCode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", nullable = false)
    private Company company;

    @Column
    private String inventoryBwCode;

    @Column
    private String partName;

    // 공급자 코드
    @Column(unique = true, nullable = false)
    private String spCode;

    // 발주 번호
    @Column(unique = true, nullable = false)
    private String poCode1;

    @Column(unique = true, nullable = false)
    private String poCode2;

    //SAP 위치
    @Column
    private String location1;

    @Column
    private String location2;

    //적입량
    @Column(nullable = false)
    private int loadAmount;

    @Column(length = 1)
    @ColumnDefault("'N'")
    private String selectYn;

    @Column(length = 1)
    @ColumnDefault("'N'")
    private String useYn;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BomPart> bomParts;

    @OneToMany(mappedBy = "part", fetch = FetchType.LAZY)
    @Where(clause = "stock > 0 and use_yn = 'Y'")
    private List<PartInventory> partInventories;

}
