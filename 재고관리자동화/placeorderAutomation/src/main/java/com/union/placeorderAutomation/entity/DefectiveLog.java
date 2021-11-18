package com.union.placeorderAutomation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DefectiveLog {

    @Id
    @Column(unique = true, nullable = false)
    private String defectiveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partLogId")
    private PartLog partLog;

    @Column
    private int amount;

}
