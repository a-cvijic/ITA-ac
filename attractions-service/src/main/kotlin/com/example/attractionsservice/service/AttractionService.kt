package com.example.attractionsservice.service

import com.example.attractionsservice.model.Attraction
import com.example.attractionsservice.repository.AttractionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AttractionService(private val repository: AttractionRepository) {
    
    private val logger = LoggerFactory.getLogger(AttractionService::class.java)

    fun getAllAttractions(): List<Attraction> = repository.findAll()

    fun getAttractionById(id: Long): Attraction? = repository.findById(id).orElse(null)

    fun createAttraction(attraction: Attraction): Attraction = repository.save(attraction)

    fun updateAttraction(id: Long, updatedAttraction: Attraction): Attraction {
        return repository.findById(id).map { existingAttraction ->
            val newAttraction = existingAttraction.copy(
                name = updatedAttraction.name,
                description = updatedAttraction.description,
                location = updatedAttraction.location
            )
            repository.save(newAttraction)
        }.orElseThrow {
            logger.error("Error updating attraction at ${Instant.now()}: Attraction not found")
            Exception("Attraction not found")
        }
    }

    fun deleteAttraction(id: Long) {
        repository.deleteById(id)
    }
}
