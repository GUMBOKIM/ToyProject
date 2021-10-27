package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "companyId", insertable = false, updatable = false, nullable = false)
    private Company company;

    @Column(unique = true, nullable = false)
    private String partName;

    // 보그워너 코드
    @Column(unique = true, nullable = false)
    private String bwCode;

    // 공급자 코드
    @Column(unique = true, nullable = false)
    private String spCode;

    // 발주 번호
    @Column(unique = true, nullable = false)
    private String poCode;

    //적입량
    @Column()
    private int loadAmount;

    //SAP 위치
    @Column()
    private String location;

    //SP
    @Column()
    private String description;

    @OneToMany
    @JoinColumn(name = "partInventoryId")
    private List<PartInventory> partInventories;
}
