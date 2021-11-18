package com.union.placeorderAutomation.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PartInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partId")
    private Part part;

    @Column(nullable = false, length = 3)
    private String lot;

    @Column(nullable = false)
    private int stock;

    @Column(length = 1)
    @ColumnDefault("'Y'")
    private String useYn;

}
