package com.union.placeorderAutomation.entity.inventory;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "materialId")
    private Material material;
}
