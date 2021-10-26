package com.union.placeorderAutomation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
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
