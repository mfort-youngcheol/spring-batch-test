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
var secondJobRunCount = 0

@ConditionalTestEnvironmentOrBatchJobName(SecondBatchJobConfiguration.JOB_NAME)
@Configuration
class SecondBatchJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    @Qualifier(JOB_NAME)
    fun secondJob(): Job {
        println("[secondJob] Job is creating...")
        return this.jobBuilderFactory.get(JOB_NAME)
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
        requireNotNull(secondParam)

        return this.stepBuilderFactory.get("secondJobStep")
            .tasklet { contribution, chunkContext ->
                println("[secondJobStep] Step is running")
                println("[secondJobStep] secondParam is `$secondParam`")
                secondJobRunCount++
                println("[secondJobStep] runCount is `${secondJobRunCount}`")
                RepeatStatus.FINISHED
            }
            .build()
    }

    companion object {
        internal const val JOB_NAME = "job.secondJob"
    }
}
