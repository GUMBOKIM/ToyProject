package com.union.placeorderAutomation.entity.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String materialCode;

    @Column(nullable = false)
    @ColumnDefault("Y")
    private String useYn;

}
