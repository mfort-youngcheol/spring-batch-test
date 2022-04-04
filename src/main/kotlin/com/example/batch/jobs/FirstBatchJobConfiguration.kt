package com.example.batch.jobs

import com.example.batch.config.condition.ConditionalTestEnvironmentOrBatchJobName
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// Job이 몇 번 실행되었는지를 count하기 위한 property
var firstJobRunCount = 0

@ConditionalTestEnvironmentOrBatchJobName(FirstBatchJobConfiguration.JOB_NAME)
@Configuration
class FirstBatchJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    @Qualifier(JOB_NAME)
    fun firstJob(): Job {
        println("[firstJob] Job is creating...")
        return this.jobBuilderFactory.get(JOB_NAME)
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
        requireNotNull(firstParam)

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

    companion object {
        internal const val JOB_NAME = "job.firstJob"
    }
}
