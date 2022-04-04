package com.example.batch.config.condition

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

class TestEnvironmentOrBatchJobNameCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        if (this.isTestProfile(context.environment.activeProfiles)) {
            return true
        }

        return this.matchesBatchJobName(context, metadata)
    }

    private fun isTestProfile(activeProfiles: Array<String>): Boolean =
        activeProfiles.any { this.isTestProfile(it) }

    private fun isTestProfile(activeProfile: String): Boolean =
        (activeProfile == TEST_PROFILE) || (activeProfile == INTEGRATION_TEST_PROFILE)

    private fun matchesBatchJobName(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        val batchJobNameAttribute = getBatchJobNameAttribute(metadata)
        val springBatchJobNamesEnvironmentVariable = this.getSpringBatchJobNamesEnvironmentVariable(context)
        return springBatchJobNamesEnvironmentVariable.contains(batchJobNameAttribute)
    }

    private fun getBatchJobNameAttribute(metadata: AnnotatedTypeMetadata): String {
        val attributes = this.getConditionalTestEnvironmentOrBatchJobNameAttributes(metadata)
        return attributes[ConditionalTestEnvironmentOrBatchJobName::batchJobName.name] as String
    }

    private fun getConditionalTestEnvironmentOrBatchJobNameAttributes(metadata: AnnotatedTypeMetadata): Map<String, Any> {
        return metadata.getAnnotationAttributes(ConditionalTestEnvironmentOrBatchJobName::class.java.name)
            ?: throw IllegalStateException("There's no ConditionalTestEnvironmentOrBatchJobName annotation")
    }

    private fun getSpringBatchJobNamesEnvironmentVariable(context: ConditionContext): List<String> {
        val property = context.environment.getProperty("spring.batch.job.names")
            ?: return listOf()
        return property.split(",")
    }

    companion object {
        private const val TEST_PROFILE = "test"
        private const val INTEGRATION_TEST_PROFILE = "intTest"
    }
}
