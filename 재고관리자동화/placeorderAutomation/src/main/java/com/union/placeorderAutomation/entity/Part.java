package com.union.placeorderAutomation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partId;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String spCode;

    @Column(unique = true, nullable = false)
    private String bwCode;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String poNumber;

    @Column(nullable = false)
    private int loadingAmount;

    @Column(nullable = false)
    private String location;

    @OneToMany
    @JoinColumn(name = "partInventoryId")
    private List<PartInventory> partInventories;
}
