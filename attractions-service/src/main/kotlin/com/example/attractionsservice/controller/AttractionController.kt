package com.example.attractionsservice.controller

import com.example.attractionsservice.model.Attraction
import com.example.attractionsservice.service.AttractionService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/attractions")
class AttractionController(private val service: AttractionService) {

    private val logger = LoggerFactory.getLogger(AttractionController::class.java)

    @GetMapping
    fun getAllAttractions(): ResponseEntity<List<Attraction>> {
        logger.info("Received request to get all attractions")
        val attractions = service.getAllAttractions()
        logger.info("Retrieved ${attractions.size} attractions")
        return ResponseEntity.ok(attractions)
    }

    @GetMapping("/{id}")
    fun getAttractionById(@PathVariable id: Long): ResponseEntity<Attraction> {
        logger.info("Received request to get attraction with id: $id")
        val attraction = service.getAttractionById(id)
        return if (attraction != null) {
            logger.info("Attraction found: $attraction")
            ResponseEntity.ok(attraction)
        } else {
            logger.warn("Attraction with id $id not found")
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createAttraction(@RequestBody attraction: Attraction): ResponseEntity<Attraction> {
        logger.info("Received request to create attraction: $attraction")
        val createdAttraction = service.createAttraction(attraction)
        logger.info("Attraction created: $createdAttraction")
        return ResponseEntity.ok(createdAttraction)
    }

    @PutMapping("/{id}")
    fun updateAttraction(@PathVariable id: Long, @RequestBody attraction: Attraction): ResponseEntity<Attraction> {
        logger.info("Received request to update attraction with id: $id")
        val updatedAttraction = service.updateAttraction(id, attraction)
        logger.info("Attraction updated: $updatedAttraction")
        return ResponseEntity.ok(updatedAttraction)
    }

    @DeleteMapping("/{id}")
    fun deleteAttraction(@PathVariable id: Long): ResponseEntity<Void> {
        logger.info("Received request to delete attraction with id: $id")
        service.getAttractionById(id)?.let {
            service.deleteAttraction(it.id)
            logger.info("Attraction with id $id deleted successfully")
            return ResponseEntity.ok().build()
        }
        logger.warn("Attraction with id $id not found for deletion")
        return ResponseEntity.notFound().build()
    }
}
