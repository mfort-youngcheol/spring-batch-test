package com.example.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@EnableBatchProcessing
@SpringBootApplication
class SpringBatchTestApplication {
    @Value("\${spring.batch.job.names:}")
    private lateinit var jobNames: String

    @PostConstruct
    fun checkBatchEnvironmentVariable() {
        require(jobNames.isNotBlank()) {
            "`spring.batch.job.names` value must not be empty"
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBatchTestApplication>(*args)
}
