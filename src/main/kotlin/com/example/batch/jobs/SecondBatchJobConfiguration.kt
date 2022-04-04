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

var secondJobRunCount = 0

@Configuration
class SecondBatchJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun secondJob(): Job {
        println("[secondJob] Job is creating...")
        return this.jobBuilderFactory.get("secondJob")
            .listener(object : JobExecutionListener {
                override fun beforeJob(jobExecution: JobExecution) {
                    println("[secondJobListener] Job is starting..!")
                }

                override fun afterJob(jobExecution: JobExecution) {
                    println("[secondJobListener] Job is finished..!")
                }
            })
            .start(this.secondJobStep())
            .build()
    }

    @Bean
    @JobScope
    fun secondJobStep(@Value("#{jobParameters[secondParam]}") secondParam: String? = null): Step {
//        requireNotNull(secondParam)

        return this.stepBuilderFactory.get("secondtJobStep")
            .tasklet { contribution, chunkContext ->
                println("[secondtJobStep] Step is running")
                println("[secondtJobStep] secondParam is `$secondParam`")
                secondJobRunCount++
                println("[secondtJobStep] runCount is `${secondJobRunCount}`")
                RepeatStatus.FINISHED
            }
            .build()
    }
}
