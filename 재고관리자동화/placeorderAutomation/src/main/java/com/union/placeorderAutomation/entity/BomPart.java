package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BomPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bom.bwCode")
    private Bom bom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part.bwCode")
    private Part part;

}
