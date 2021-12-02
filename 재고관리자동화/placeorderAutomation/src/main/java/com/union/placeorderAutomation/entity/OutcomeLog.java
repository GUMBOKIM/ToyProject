package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OutcomeLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outcomeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwCode")
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyCode")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantCode")
    private Plant plant;


    @Column(nullable = false, length = 8)
    private String date;

    @Column
    private String lot;

    @Column
    private int amount;

    @Column
    private int orderSeq;
}
