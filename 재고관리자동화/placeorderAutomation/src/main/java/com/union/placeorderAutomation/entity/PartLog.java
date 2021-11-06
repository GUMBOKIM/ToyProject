package com.union.placeorderAutomation.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PartLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partId")
    private Part part;

    //I => 입고, O => 출고, M => 보정
    @Column(nullable = false)
    private String division;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column
    private String time;

    @Column
    private String plant;

}
