package cn.devicelinks.framework.common.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.lang.annotation.Annotation;

/**
 * 注解AOP切入点配置类
 *
 * @author 恒宇少年
 */
public class AnnotationPointcutAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;
    private final Pointcut pointcut;

    public AnnotationPointcutAdvisor(Advice advice, Class<? extends Annotation> annotationClass) {
        Pointcut cpc = new AnnotationMatchingPointcut(annotationClass, true);
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(annotationClass);
        ComposablePointcut composablePointcut = new ComposablePointcut(cpc);
        this.pointcut = composablePointcut.union(mpc);
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }
}
