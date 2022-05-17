package ua.tunepoint.recommendation.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ua.tunepoint.recommendation.utils.LoggingConstants.ACTION_NAME;
import static ua.tunepoint.recommendation.utils.LoggingConstants.ARGUMENTS;
import static ua.tunepoint.recommendation.utils.LoggingConstants.COMPLETED;
import static ua.tunepoint.recommendation.utils.LoggingConstants.ERROR_DESCRIPTION;
import static ua.tunepoint.recommendation.utils.LoggingConstants.FAILED;
import static ua.tunepoint.recommendation.utils.LoggingConstants.RESULT;
import static ua.tunepoint.recommendation.utils.LoggingConstants.STARTED;

@Aspect
@Configuration
@Slf4j(topic = "businessOperationLogger")
public class BusinessLoggingAspect {

    @Around("blOperations()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getStaticPart().getSignature().getName();
        MDC.put(ACTION_NAME, methodName);

        log.info("Service method invoked '{}'", methodName);

        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0){
            CodeSignature signature = (CodeSignature)  joinPoint.getSignature();
            String[] parameterNames = signature.getParameterNames();

            String argsString = normalizeArguments(IntStream.range(0, args.length)
                    .mapToObj(i -> String.format("%s=%s", parameterNames[i], args[i]))
                    .collect(Collectors.joining("; "))
            );

            MDC.put(ARGUMENTS, argsString);
        }

        log.info(STARTED);

        return joinPoint.proceed();
    }

    @AfterReturning(value = "blOperations()", returning = "result")
    public void afterReturning(Object result){
        if (result != null) {
            MDC.put(RESULT, result.toString());
        }

        log.info(COMPLETED);

        MDC.clear();
    }

    @AfterThrowing(value = "blOperations()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception){

        String errorDescription = exception.getMessage();
        MDC.put(ERROR_DESCRIPTION, errorDescription);
        log.info(FAILED);

        MDC.clear();
    }

    @Pointcut("execution (* ua.tunepoint.recommendation.service.ModelService.*(..))")
    private void blOperations(){}

    private String normalizeArguments(String args){
        return "("  + args + ")";
    }
}
