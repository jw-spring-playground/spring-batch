package kr.co.practice.batch.tasklet

import mu.KotlinLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import java.io.File

class DeleteOldFilesTasklet(
    private val path: String?,
    private val daysOld: Int,
) : Tasklet {
    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext,
    ): RepeatStatus? {
        val dir: File = File(path)
        val cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L)

        val files: Array<File>? = dir.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.lastModified() < cutoffTime) {
                    if (file.delete()) {
                        kLogger.info("🔥 파일 삭제: {}", file.getName())
                    } else {
                        kLogger.info("⚠️  파일 삭제 실패: {}", file.getName())
                    }
                }
            }
        }
        return RepeatStatus.FINISHED
    }

    companion object {
        val kLogger = KotlinLogging.logger {}
    }
}
