package kr.co.practice.batch.config

import kr.co.practice.batch.tasklet.BasicTasklet
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
class BasicBatchConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {
    @Bean
    fun processCleanupTasklet(): Tasklet = BasicTasklet()

    /**
     *  DB 트랜잭션을 사용하지 않는 배치인 경우
     *  실제 DB 커넥션을 관리하는 일반적인 PlatformTransactionManager 구현체를 사용하는 대신
     *  ResourcelessTransactionManager라는 옵션을 고려해볼만 하다
     **/
    @Bean
    fun basicCleanupStep(): Step =
        StepBuilder("basicCleanupStep", jobRepository)
            .tasklet(processCleanupTasklet(), transactionManager) // ResourcelessTransactionManager()
            .build()

    @Bean
    fun basicCleanupJob(): Job =
        JobBuilder("basicCleanupJob", jobRepository)
            .start(basicCleanupStep()) // Step 등록
            .build()
}
