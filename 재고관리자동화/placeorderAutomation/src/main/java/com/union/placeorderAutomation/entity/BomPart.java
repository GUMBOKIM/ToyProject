package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BomPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bomPartId;

    @ManyToOne
    @JoinColumn(name = "bomId")
    private Bom bom;

    @ManyToOne
    @JoinColumn(name = "partId")
    private Part part;
}
