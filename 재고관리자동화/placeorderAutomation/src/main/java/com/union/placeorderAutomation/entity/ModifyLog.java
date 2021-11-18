package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ModifyLog {

    @Id
    @Column(unique = true, nullable = false)
    private String modifyId;

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
