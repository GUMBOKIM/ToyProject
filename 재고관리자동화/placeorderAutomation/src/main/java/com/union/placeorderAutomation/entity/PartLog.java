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
public class PartLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partLogId;

    //I => 입고, O => 출고, M => 보정
    @Column(columnDefinition = "nvarchar(1) check (div in ('I','O','M')", nullable = false)
    private String div;

    @Column(nullable = false)
    private int amount;

    @Column
    private String date;

    @Column
    private String time;
}
