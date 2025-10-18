package com.sohamkamani.spring_rag_demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Deluxe, Suite...

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "capacity_adults")
    private Integer capacityAdults;

    @Column(name = "capacity_children")
    private Integer capacityChildren;

    @Column(name = "size_m2")
    private Double sizeM2;

    private Boolean refundable;

    private Integer floor;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;
}
