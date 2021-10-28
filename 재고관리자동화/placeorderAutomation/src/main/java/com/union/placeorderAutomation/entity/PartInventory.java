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
public class PartInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partInventoryId;

    @ManyToOne
    @JoinColumn(name = "partId")
    private Part part;

    @Column(nullable = false)
    private String lot;

    @Column(nullable = false)
    private int stock;

    @Column(columnDefinition = "nvarchar(1) default 'Y'")
    private String useYn;
}
