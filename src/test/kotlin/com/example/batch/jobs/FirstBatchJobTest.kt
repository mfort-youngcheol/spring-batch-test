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
class FirstBatchJobTest {

    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    @Qualifier("firstJob")
    private lateinit var firstJob: Job

    @BeforeEach
    fun init() {
        this.jobLauncherTestUtils.job = firstJob
    }

    @Test
    fun firstJobTest() {
        println("[FirstBatchJobTest] Test is started")

        this.jobLauncherTestUtils.launchJob(
            this.jobLauncherTestUtils.uniqueJobParametersBuilder
                .addString("firstParam", "1st")
                .toJobParameters()
        )

        assertThat(firstJobRunCount).isEqualTo(1)

        println("[FirstBatchJobTest] Test is finished")
    }
}
