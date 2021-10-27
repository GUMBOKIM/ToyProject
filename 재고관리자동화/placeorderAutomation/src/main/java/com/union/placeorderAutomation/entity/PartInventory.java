package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PartInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partInventoryId;

    @ManyToOne
    @JoinColumn(name = "partId")
    private Part part;

    @Column(nullable = false)
    private String lot;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean plantCode;


}
