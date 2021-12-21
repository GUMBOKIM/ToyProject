package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DefectiveLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long defectiveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwCode")
    private Part part;

    @Column(nullable = false, length = 8)
    private String date;

    @Column
    private int amount;

}
