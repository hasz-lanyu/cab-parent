package org.cab.admin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.cab.common.resp.Result;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ParamValidAspect {
    @Pointcut("@annotation(org.cab.admin.anno.CheckParam)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object paramValid(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        BindingResult bindingResult = null;
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                bindingResult = (BindingResult) arg;
            }
        }

        if (bindingResult != null && bindingResult.hasErrors()) {
            return Result.error(bindingResult.getFieldError().getDefaultMessage());
        }

        return joinPoint.proceed();
    }
}
