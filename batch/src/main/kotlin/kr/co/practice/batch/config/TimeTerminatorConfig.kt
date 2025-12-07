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
import java.time.LocalDate
import java.time.LocalDateTime

@Configuration
class TimeTerminatorConfig {
    @Bean
    fun timeTerminatorJob(
        jobRepository: JobRepository,
        timeTerminationStep: Step,
    ): Job =
        JobBuilder("timeTerminatorJob", jobRepository)
            .start(timeTerminationStep)
            .build()

    @Bean
    fun timeTerminationStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        timeTerminatorTasklet: Tasklet,
    ): Step =
        StepBuilder("timeTerminationStep", jobRepository)
            .tasklet(timeTerminatorTasklet, transactionManager)
            .build()

    /**
     *  localDateTime, LocalDate 모두 가능하다.
     *  ZonedDateTime은 지원하지않는다.
     *  전달할 때는 ISO 표준으로 전달해야한다.
     **/
    @Bean
    @StepScope
    fun timeTerminatorTasklet(
        @Value("#{jobParameters['executionDate']}") executionDate: LocalDate,
        @Value("#{jobParameters['startTime']}") startTime: LocalDateTime,
    ): Tasklet =
        Tasklet { contribution, chunkContext ->
            kLogger.info("실행자 정보:")
            kLogger.info("실행 일시: {}", executionDate)
            kLogger.info("시작 시각: {}", startTime)

            kLogger.info("🎯 임무 완료: 모든 대상 프로세스가 종료되었습니다.")
            RepeatStatus.FINISHED
        }

    companion object {
        val kLogger = KotlinLogging.logger {}
    }
}
