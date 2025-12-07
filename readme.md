# 🚀 Spring Batch Practice

Kotlin으로 구현한 Spring Batch 학습 프로젝트입니다.

## 📋 프로젝트 개요

- **언어**: Kotlin
- **프레임워크**: Spring Boot 3.5.5, Spring Batch
- **빌드 도구**: Gradle (Kotlin DSL)

## 🎯 구현된 Job 실행 방법

### 특정 Job 실행
```bash
# 기본 정리 Job 실행
./gradlew bootRun --args="--spring.batch.job.name=basicCleanupJob"

# 파일 삭제 Job 실행
./gradlew bootRun --args="--spring.batch.job.name=deleteOldFilesJob"

# 프로세스 종료 Job 실행 (파라미터 포함)
./gradlew bootRun --args="--spring.batch.job.name=processTerminatorJob terminatorId=hello targetCount=5"

# pojo로 파라미터를 넣었을 떄
bootRun --args='--spring.batch.job.name=jobParameterJob startDateTime=2024-07-25T10:00:00,java.time.LocalDateTime teminateId=100,java.lang.Long status=START,kr.co.practice.batch.tasklet.command.JobParameterStatus name=daily-report'
```

### JAR 파일로 실행
```bash
# 빌드
./gradlew build

# 실행
java -jar build/libs/batch-0.0.1-SNAPSHOT.jar --spring.batch.job.name=basicCleanupJob
```

## 🗄️ 데이터베이스

### H2 콘솔 접속
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (공백)

### 배치 메타데이터 테이블
Spring Batch가 자동으로 생성하는 테이블들:
- `BATCH_JOB_INSTANCE`
- `BATCH_JOB_EXECUTION`
- `BATCH_STEP_EXECUTION`
- `BATCH_JOB_EXECUTION_PARAMS`
