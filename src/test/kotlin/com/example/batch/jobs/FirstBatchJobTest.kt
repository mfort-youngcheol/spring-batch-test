package com.example.batch.jobs

import com.example.batch.BatchTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FirstBatchJobTest : BatchTest() {
    @Test
    fun firstJobTest() {
        println("[FirstBatchJobTest] Test is started")

        // 이런 식으로 배치 Job 관련 테스트 코드 중복을 없앨 수 있을 것 같다.
        super.launchJob(
            FirstBatchJobConfiguration.JOB_NAME,
            super.uniqueJobParametersBuilder()
                .addString("firstParam", "1st")
                .toJobParameters()
        )

        assertThat(firstJobRunCount).isEqualTo(1)

        println("[FirstBatchJobTest] Test is finished")
    }
}

//@BatchTestSupport
//class FirstBatchJobTest {
//
//    @Autowired
//    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils
//
//    // 테스트하고자 하는 Job bean을 가져다 JobLauncherTestUtils.job을 변경해야한다.
//    // 코드 중복은 아쉬운 부분이나 더 좋은 방법은 딱히 생각나진 않는다.
//    @Autowired
//    @Qualifier(FirstBatchJobConfiguration.JOB_NAME)
//    private lateinit var firstJob: Job
//
//    @BeforeEach
//    fun init() {
//        this.jobLauncherTestUtils.job = firstJob
//    }
//
//    @Test
//    fun firstJobTest() {
//        println("[FirstBatchJobTest] Test is started")
//
//        this.jobLauncherTestUtils.launchJob(
//            this.jobLauncherTestUtils.uniqueJobParametersBuilder
//                .addString("firstParam", "1st")
//                .toJobParameters()
//        )
//
//        assertThat(firstJobRunCount).isEqualTo(1)
//
//        println("[FirstBatchJobTest] Test is finished")
//    }
//}
