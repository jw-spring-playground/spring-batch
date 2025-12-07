package kr.co.practice.batch.config

import kr.co.practice.batch.tasklet.ProgramJobParameterTasklet
import kr.co.practice.batch.tasklet.command.JobParameterCommand
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class ProgramJobParameterBatchConfig {
    @Bean
    fun programJobParameterTasklet(): Tasklet = ProgramJobParameterTasklet()

    @Bean
    fun programJobParameterStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        programJobParameterTasklet: Tasklet,
    ): Step =
        StepBuilder("programJobParameterStep", jobRepository)
            .tasklet(programJobParameterTasklet, transactionManager)
            .build()

    @Bean
    fun programJobParameterJob(
        jobRepository: JobRepository,
        programJobParameterStep: Step,
    ): Job =
        JobBuilder("programJobParameterJob", jobRepository)
            .validator(
                DefaultJobParametersValidator(
                    arrayOf("name"),
                    arrayOf("target"),
                ),
            ).start(programJobParameterStep)
            .build()
}
