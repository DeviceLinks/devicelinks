package cn.devicelinks.console.configuration;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.List;

/**
 * 全局事务配置
 * <p>
 * 根据业务逻辑方法名称规则匹配进行统一添加事务
 * 对于数据新增、更新、删除操作使用默认隔离规则{@link TransactionDefinition#PROPAGATION_REQUIRED}的事务
 * 对于查询、统计等操作方法则使用只读规则{@link TransactionDefinition#PROPAGATION_NOT_SUPPORTED}的事务
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Aspect
@Configuration
@AllArgsConstructor
public class GlobalTransactionConfiguration {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* *..service..*.*(..))";

    private static final int TX_METHOD_TIMEOUT = 20;
    /**
     * 匹配{@link TransactionDefinition#PROPAGATION_REQUIRED}隔离规则事务的方法名称
     */
    private static final List<String> DEFAULT_REQUIRED_TRANSACTION_METHOD_PATTERNS = List.of(
            "save*", "add*", "insert*", "update*", "delete*", "remove*"
    );
    /**
     * 匹配{@link TransactionDefinition#PROPAGATION_NOT_SUPPORTED}隔离规则事务的方法名称
     */
    private static final List<String> READONLY_TRANSACTION_METHOD_PATTERNS = List.of(
            "get*", "query*", "find*", "list*", "select*", "count*"
    );

    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor globalTransactionAdvice() {
        DefaultTransactionAttribute propagationRequired = new DefaultTransactionAttribute();
        propagationRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        propagationRequired.setTimeout(TX_METHOD_TIMEOUT);

        DefaultTransactionAttribute propagationReadonly = new DefaultTransactionAttribute();
        propagationReadonly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        propagationReadonly.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        // PROPAGATION_REQUIRED
        DEFAULT_REQUIRED_TRANSACTION_METHOD_PATTERNS.forEach(methodPattern ->
                source.addTransactionalMethod(methodPattern, propagationRequired));

        // PROPAGATION_NOT_SUPPORTED
        READONLY_TRANSACTION_METHOD_PATTERNS.forEach(methodPattern ->
                source.addTransactionalMethod(methodPattern, propagationReadonly));

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor globalTransactionAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, globalTransactionAdvice());
    }
}
