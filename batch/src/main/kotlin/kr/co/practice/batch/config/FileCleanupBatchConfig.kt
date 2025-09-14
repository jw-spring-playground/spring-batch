package kr.co.practice.batch.config

import kr.co.practice.batch.tasklet.DeleteOldFilesTasklet
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
class FileCleanupBatchConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {
    @Bean
    fun deleteOldFilesTasklet(): Tasklet {
        // "temp" 디렉토리에서 30일 이상 지난 파일 삭제
        return DeleteOldFilesTasklet("/path/to/temp", 30)
    }

    @Bean
    fun deleteOldFilesStep(): Step =
        StepBuilder("deleteOldFilesStep", jobRepository)
            .tasklet(deleteOldFilesTasklet(), transactionManager)
            .build()

    @Bean
    fun deleteOldFilesJob(): Job =
        JobBuilder("deleteOldFilesJob", jobRepository)
            .start(deleteOldFilesStep())
            .build()
}
