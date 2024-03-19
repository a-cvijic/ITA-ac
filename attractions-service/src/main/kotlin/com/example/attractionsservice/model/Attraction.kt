package com.example.attractionsservice.model

import javax.persistence.*

@Entity
@Table(name = "attractions")
data class Attraction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var location: String

)
