package com.union.placeorderAutomation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
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
