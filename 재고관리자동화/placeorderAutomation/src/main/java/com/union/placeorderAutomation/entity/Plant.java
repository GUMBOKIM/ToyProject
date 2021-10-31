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
public class Plant {

    @Id
    @Column(nullable = false)
    private String plantCode;


    @Column(unique = true, nullable = false)
    private String plantName;


}