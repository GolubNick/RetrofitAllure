package utils;

import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import okhttp3.Request;
import okhttp3.internal.http.RealInterceptorChain;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;

import java.util.UUID;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.util.ResultsUtils.getStatus;
import static io.qameta.allure.util.ResultsUtils.getStatusDetails;

@Aspect
public class Aspects {

    @Around("execution(* utils.CustomLoggingInterceptor.intercept(..))")
    public Object stepIntercept(ProceedingJoinPoint joinPoint) throws Throwable {
        Request request = ((RealInterceptorChain) joinPoint.getArgs()[0]).request();
        final String name = request.method() + " " + request.url();
        final String uuid = UUID.randomUUID().toString();
        final StepResult result = new StepResult()
                .setName(name);
        getLifecycle().startStep(uuid, result);
        try {
            final Object proceed = joinPoint.proceed();
            getLifecycle().updateStep(uuid, s -> s.setStatus(Status.PASSED));
            return proceed;
        } catch (Throwable e) {
            getLifecycle().updateStep(uuid, s -> s
                    .setStatus(getStatus(e).orElse(Status.BROKEN))
                    .setStatusDetails(getStatusDetails(e).orElse(null)));
            throw e;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }
}