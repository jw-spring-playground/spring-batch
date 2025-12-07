## 커맨드라인 파라미터가 Job에 전달되는 과정

여기서 커맨드 라인이란?
```
./gradlew bootRun --args="--spring.batch.job.name=processTerminatorJob ~~
```
이런식으로 명령어 기반으로 잡을 실행시킬 때

spring이 실행할 때 JobLauncherApplicationRunner 컴포넌트가 자동으로 동작

**JobLauncherApplicationRunner** 이 친구가 잡 파라미터를 해석하고 Job을 실행하는 역하을 맡고 있다.

1. Job 목록을 준비
2. 유효성 검증
   - Job 타입의 빈들을 검증한다.
3. 명령어 해석
   - 커맨드라인으로 전달된 값들을 파싱한다.
4. Job을 실행
