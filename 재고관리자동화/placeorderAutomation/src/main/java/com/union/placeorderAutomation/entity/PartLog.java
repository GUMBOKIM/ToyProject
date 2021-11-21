package com.union.placeorderAutomation.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "bwCode")
    private Part part;

    @Column(nullable = false, length = 8)
    private String date;

    @OneToMany(mappedBy = "partLog")
    private List<DefectiveLog> defectiveLogs = new ArrayList<>();

    @OneToMany(mappedBy = "partLog")
    private List<IncomeLog> incomeLogs = new ArrayList<>();

    @OneToMany(mappedBy = "partLog")
    private List<OutcomeLog> outcomeLogs = new ArrayList<>();

    @OneToMany(mappedBy = "partLog")
    private List<ModifyLog> modifyLogs = new ArrayList<>();

    public void addDefectiveLogs(DefectiveLog defectiveLog){
        defectiveLog.setPartLog(this);
        this.defectiveLogs.add(defectiveLog);
    }

    public void addIncomeLogs(IncomeLog incomeLogs){
        incomeLogs.setPartLog(this);
        this.incomeLogs.add(incomeLogs);
    }

    public void addOutcomeLogs(OutcomeLog outcomeLogs){
        outcomeLogs.setPartLog(this);
        this.outcomeLogs.add(outcomeLogs);
    }

    public void addModifyLogs(ModifyLog modifyLogs){
        modifyLogs.setPartLog(this);
        this.modifyLogs.add(modifyLogs);
    }
}
