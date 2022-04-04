package com.example.batch

import org.junit.jupiter.api.BeforeEach
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.NoSuchJobException
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.JobRepositoryTestUtils
import org.springframework.beans.factory.annotation.Autowired

@BatchTestSupport
class BatchTest {
    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    private lateinit var jobRepositoryTestUtils: JobRepositoryTestUtils

    @Autowired
    private lateinit var jobBeans: List<Job>

    protected fun uniqueJobParametersBuilder() = this.jobLauncherTestUtils.uniqueJobParametersBuilder

    protected fun launchJob(jobName: String, jobParameters: JobParameters) {
        this.jobLauncherTestUtils.job = this.getJob(jobName)
        this.jobLauncherTestUtils.launchJob(jobParameters)
    }

    protected fun getJob(jobName: String): Job =
        this.getJobs()[jobName] ?: throw NoSuchJobException("There's no job with the name - `$jobName`")

    private fun getJobs(): Map<String, Job> =
        this.jobBeans.associateBy { it.name }

    protected fun removeJobExecutions() = this.jobRepositoryTestUtils.removeJobExecutions()

    @BeforeEach
    fun beforeeach() {
        println("[BatchTest] removeJobExecutions before test")
        this.removeJobExecutions()
    }
}
