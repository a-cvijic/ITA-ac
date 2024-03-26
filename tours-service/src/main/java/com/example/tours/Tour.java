package com.example.tours;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;

@Entity
@Table(name = "tours")
@SequenceGenerator(name = "tour_sequence", sequenceName = "tours_id_seq", allocationSize = 1)
public class Tour extends PanacheEntity {

    @Column(length = 100, nullable = false)
    public String name;

    @Column(length = 500)
    public String description;

    @Column(columnDefinition = "text")
    public String highlights;

    @Column(length = 100)
    public String location;

}
