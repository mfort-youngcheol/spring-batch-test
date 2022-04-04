package com.example.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBatchTest
@SpringBootTest
annotation class BatchTestSupport

@Configuration
class EmptyJobForTestConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Primary
    @Bean
    fun job(): Job {
        return this.jobBuilderFactory.get("emptyJob")
            .start(this.stepBuilderFactory.get("emptyJobStep")
                .tasklet { contribution, chunkContext -> RepeatStatus.FINISHED }
                .build()
            )
            .build()
    }
}
