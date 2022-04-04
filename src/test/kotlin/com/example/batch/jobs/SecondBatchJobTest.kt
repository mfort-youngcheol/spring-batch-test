package com.example.batch.jobs

import com.example.batch.BatchTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.Job
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

@BatchTestSupport
class SecondBatchJobTest {

    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    @Qualifier("secondJob")
    private lateinit var secondJob: Job

    @BeforeEach
    fun init() {
        this.jobLauncherTestUtils.job = secondJob
    }

    @Test
    fun secondJobTest() {
        println("[SecondBatchJobTest] Test is started")

        this.jobLauncherTestUtils.launchJob(
            this.jobLauncherTestUtils.uniqueJobParametersBuilder
                .addString("secondParam", "2nd")
                .toJobParameters()
        )

        assertThat(secondJobRunCount).isEqualTo(1)

        println("[SecondBatchJobTest] Test is finished")
    }
}
