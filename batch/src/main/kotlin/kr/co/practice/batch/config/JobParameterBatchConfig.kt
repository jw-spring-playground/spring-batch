package kr.co.practice.batch.config

import kr.co.practice.batch.tasklet.JobParameterTasklet
import kr.co.practice.batch.tasklet.command.JobParameterCommand
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JobParameterBatchConfig {
    @Bean
    fun jobParameterTasklet(command: JobParameterCommand): Tasklet = JobParameterTasklet(command)

    @Bean
    fun jobParameterStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        jobParameterTasklet: Tasklet,
    ): Step =
        StepBuilder("jobParameterStep", jobRepository)
            .tasklet(jobParameterTasklet, transactionManager)
            .build()

    @Bean
    fun jobParameterJob(
        jobRepository: JobRepository,
        jobParameterStep: Step,
    ): Job =
        JobBuilder("jobParameterJob", jobRepository)
            .start(jobParameterStep)
            .build()
}
