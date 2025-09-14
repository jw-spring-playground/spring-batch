package kr.co.practice.batch.config

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SystemTerminatorConfig {
    @Bean
    fun processTerminatorJob(
        jobRepository: JobRepository,
        terminationStep: Step,
    ): Job =
        JobBuilder("processTerminatorJob", jobRepository)
            .start(terminationStep)
            .build()

    @Bean
    fun terminationStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        terminatorTasklet: Tasklet,
    ): Step =
        StepBuilder("terminationStep", jobRepository)
            .tasklet(terminatorTasklet, transactionManager)
            .build()

    @Bean
    @StepScope
    fun terminatorTasklet(
        @Value("#{jobParameters['terminatorId']}") terminatorId: String,
        @Value("#{jobParameters['targetCount']}") targetCount: Int,
    ): Tasklet =
        Tasklet { contribution, chunkContext ->
            kLogger.info("실행자 정보:")
            kLogger.info("ID: {}", terminatorId)
            kLogger.info("제거 대상 수: {}", targetCount)
            kLogger.info("⚡ SYSTEM TERMINATOR {} 개시합니다.", terminatorId)
            kLogger.info("☠️ {}개의 프로세스를 종료합니다.", targetCount)

            for (i in 1..targetCount) {
                kLogger.info("💀 프로세스 {} 종료 완료!", i)
            }

            kLogger.info("🎯 임무 완료: 모든 대상 프로세스가 종료되었습니다.")
            RepeatStatus.FINISHED
        }

    companion object {
        val kLogger = KotlinLogging.logger {}
    }
}
