package com.example.batch.jobs

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

var firstJobRunCount = 0

@Configuration
class FirstBatchJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun firstJob(): Job {
        println("[firstJob] Job is creating...")
        return this.jobBuilderFactory.get("firstJob")
            .listener(object : JobExecutionListener {
                override fun beforeJob(jobExecution: JobExecution) {
                    println("[firstJobListener] Job is starting..!")
                }

                override fun afterJob(jobExecution: JobExecution) {
                    println("[firstJobListener] Job is finished..!")
                }
            })
            .start(this.firstJobStep())
            .build()
    }

    @Bean
    @JobScope
    fun firstJobStep(@Value("#{jobParameters[firstParam]}") firstParam: String? = null): Step {
//        requireNotNull(firstParam)

        return this.stepBuilderFactory.get("firstJobStep")
            .tasklet { contribution, chunkContext ->
                println("[firstJobStep] Step is running")
                println("[firstJobStep] firstParam is `$firstParam`")
                firstJobRunCount++
                println("[firstJobStep] runCount is `${firstJobRunCount}`")
                RepeatStatus.FINISHED
            }
            .build()
    }
}
