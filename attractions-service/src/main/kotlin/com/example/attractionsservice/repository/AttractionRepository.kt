package com.example.attractionsservice.repository

import com.example.attractionsservice.model.Attraction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AttractionRepository : JpaRepository<Attraction, Long> {
}
