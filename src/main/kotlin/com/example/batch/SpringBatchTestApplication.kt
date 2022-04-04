package com.example.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@EnableBatchProcessing
@SpringBootApplication
class SpringBatchTestApplication {
    @Value("\${${SPRING_BATCH_JOB_NAMES_PROPERTY}:}")
    private lateinit var jobNames: String

    @PostConstruct
    fun checkBatchEnvironmentVariable() {
        require(jobNames.isNotBlank()) {
            "`${SPRING_BATCH_JOB_NAMES_PROPERTY}` value must not be empty"
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBatchTestApplication>(*args)
}
