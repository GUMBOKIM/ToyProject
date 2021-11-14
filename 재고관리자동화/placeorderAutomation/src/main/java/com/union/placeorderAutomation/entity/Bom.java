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
    @Column
    private String bwCode;

    @OneToMany(mappedBy = "bom", fetch = FetchType.LAZY)
    @OrderBy("part asc")
    private List<BomPart> bomParts;

}
