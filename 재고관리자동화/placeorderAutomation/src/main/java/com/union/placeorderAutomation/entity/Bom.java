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
