package kr.co.practice.batch.tasklet.command

import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@StepScope
@Component
data class JobParameterCommand(
    @Value("#{jobParameters['startDateTime']}")
    val startDateTime: LocalDateTime,
    @Value("#{jobParameters['teminateId']}")
    val terminateId: Long,
    @Value("#{jobParameters['status']}")
    val status: JobParameterStatus,
    @Value("#{jobParameters['name']}")
    val name: String,
)

enum class JobParameterStatus {
    START,
    END,
}
