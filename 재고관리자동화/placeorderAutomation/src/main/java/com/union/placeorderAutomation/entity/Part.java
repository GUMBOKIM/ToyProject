package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



@Getter
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
    private String partName;

    // 공급자 코드
    @Column(unique = true, nullable = false)
    private String spCode;

    // 발주 번호
    @Column(unique = true, nullable = false)
    private String poCode;

    //적입량
    @Column(nullable = false)
    private int loadAmount;

    //SAP 위치
    @Column
    private String location;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BomPart> bomParts;

    @OneToMany(mappedBy = "part", fetch = FetchType.LAZY)
    @OrderBy("lot asc")
    @Where(clause = "stock > 0 and use_yn = 'Y'")
    private List<PartInventory> partInventories;

    @OneToMany(mappedBy = "part", fetch = FetchType.LAZY)
    private List<PartLog> partLogs;

    public int sumStock(){
        int stock = 0;
        for (int i = 0; i < partInventories.size(); i++){
            stock += partInventories.get(i).getStock();
        }
        return stock;
    }
}
