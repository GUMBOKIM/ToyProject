package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ModifyLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modifyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partLogId")
    private PartLog partLog;

    @Column
    private String lot;

    @Column
    private int amount;

    @Column
    private int orderSeq;
}
