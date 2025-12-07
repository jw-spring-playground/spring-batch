package kr.co.practice.batch.tasklet

import mu.KotlinLogging
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

/**
 * 프로그램 방식 테스크
 * REST API, QUEUE, 스케줄러 등등
 **/
class ProgramJobParameterTasklet : Tasklet {
    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext,
    ): RepeatStatus? {
        val jobParameters: JobParameters? =
            chunkContext.stepContext
                .stepExecution
                .getJobParameters()

        val name = jobParameters!!.getString("name")
        val target = jobParameters.getString("target") ?: "없음"

        kLogger.info { "이름: $name" }
        kLogger.info { "타겟: $target" }

        return RepeatStatus.FINISHED
    }

    companion object {
        private val kLogger = KotlinLogging.logger { }
    }
}
