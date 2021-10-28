package com.union.placeorderAutomation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bomId;

    @Column(unique = true, nullable = false)
    private String bwCode;

    @OneToMany(mappedBy = "bom", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BomPart> bomParts = new ArrayList<>();

}
