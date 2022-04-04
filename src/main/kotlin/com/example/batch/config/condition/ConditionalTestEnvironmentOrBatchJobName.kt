package com.example.batch.config.condition

import org.springframework.context.annotation.Conditional

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Conditional(TestEnvironmentOrBatchJobNameCondition::class)
annotation class ConditionalTestEnvironmentOrBatchJobName(
    val batchJobName: String
)
