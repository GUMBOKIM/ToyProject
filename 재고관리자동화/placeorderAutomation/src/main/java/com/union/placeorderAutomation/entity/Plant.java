package com.union.placeorderAutomation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long plantId;

    @Column(unique = true, nullable = false)
    private String plantName;

    @Column(nullable = false)
    private String plantCode;

}