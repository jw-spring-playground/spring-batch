package kr.co.practice.batch.tasklet

import kr.co.practice.batch.tasklet.command.JobParameterCommand
import mu.KotlinLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class JobParameterTasklet(
    val command: JobParameterCommand,
) : Tasklet {
    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext,
    ): RepeatStatus? {
        kLogger.info { "이름: ${command.name}" }
        kLogger.info { "시작 시각: ${command.startDateTime}" }
        kLogger.info { "현재 상태: ${command.status}" }
        kLogger.info { "id: ${command.terminateId}" }

        return RepeatStatus.FINISHED
    }

    companion object {
        private val kLogger = KotlinLogging.logger { }
    }
}
