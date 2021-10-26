package com.union.placeorderAutomation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Bom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bomId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String bwCode;

    @OneToMany
    @JoinColumn(name = "materialId")
    private List<BomPart> bomParts;

}
