package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {

    @Id
    @Column(unique = true, nullable = false)
    private String companyCode;

    @Column(unique = true, nullable = false)
    private String companyName;



    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<Part> parts = new ArrayList<>();

}
