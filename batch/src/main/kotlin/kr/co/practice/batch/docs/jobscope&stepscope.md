## Job & Step scope

> JobScope와 StepScope는 애플리케이션 구동 시점에는 프록시 객체로만 존재   
> (프록시 객체에 접근할 때 실제 빈 생성 -> 실행 후에 삭제)


### @JobScope

JobExecution과 생명주기를 같이 한다.

- 실행 시점에 빈이 생성되면서 전달되는 파라미터를 받을 수 있게 된다.
- 동시에 여러 요청이 같은 Job으로 들어와도 서로 다른 잡으로 실행될 수 있게 된다.

### @StepScope

Tasklet에서 사용   
Step 실행 범위안에서만 빈을 관리한다.

- 마찬가지로 한 스탭이 여러 잡에 의해 실행될 때 독립성이 보장된다.


### 🚨 주의 사항

1. 프록시 대상의 타입이 클래스인 경우 반드시 상속이 가능한 클래스여야 한다.
2. step 빈에는 @StepScope와 @JobScope를 사용하면 안된다.
    - @StepScope 같은 경우는 step이 실행되기 전에 빈에 접근하려고 하다보니 아직 생성되지 않았기 때문에 폭발한다.
    - @JobScope 같은 경우는 권장하지 않는 방법으로 에러가 나진 않는다.
      - 복잡한 상황에서 문제가 생길 수 있음
      - Step에서 tasklet을 주입받아서 쓰고 StepScope를 tasklet에 붙여준다.


### ExecutionContext

비즈니스 로직 처리 중 발생하는 커스텀한 데이터들을 관리할 방법으로 사용하는 데이터 컨테이너

- 집계 중간 결과물 같은 데이터를 저장할 수 있다.
- Job 중단 후 재시작할 때 특히 유용하다.

접근 방법
```kotlin
@Bean
@StepScope
fun targetTasklet(
    @Value("#{stepExecutionContext['targetSystemStatus']}") 
    targetStatus: String
): Tasklet {
}
```

- job은 모든 컴포넌트에서 접근 가능 (step도 가능)
- step 수준의 데이터는 Job 수준에서 가져올 수 없고,
- step ExecutionContext는 다른 step에서는 접근할 수 없다.