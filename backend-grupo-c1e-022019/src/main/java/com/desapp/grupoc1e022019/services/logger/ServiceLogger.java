package com.desapp.grupoc1e022019.services.logger;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

@Aspect
@Configuration
@Order(0)
public class ServiceLogger {

    private static Logger logger = Logger.getLogger(ServiceLogger.class);

    @Before("within(com.desapp.grupoc1e022019.services.controllers.*)")
    public void before(JoinPoint joinPoint) {
        logger.info(
                "Class: "+ joinPoint.getThis() +
                ", Method: " + joinPoint.getSignature().getName() +
                ", Args: " + Arrays.toString(joinPoint.getArgs())) ;
    }
}
