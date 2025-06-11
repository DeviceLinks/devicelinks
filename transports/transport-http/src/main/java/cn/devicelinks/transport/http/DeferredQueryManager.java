package cn.devicelinks.transport.http;

import cn.devicelinks.transport.support.model.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * {@link DeferredResult}阻塞查询管理器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public final class DeferredQueryManager {
    private static final long RETRY_PULL_INTERVAL = 1000L;
    private final ConcurrentMap<String, List<DeferredResult<MessageResponse>>> waitingMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    /**
     * 执行异步阻塞数据查询
     *
     * @param key                异步查询唯一Key
     * @param timeout            查询超时时间
     * @param messageId          请求消息唯一ID
     * @param queryFunction      查询业务数据函数
     * @param dataReadyPredicate 查询结果是否有效，如果有效则执行{@link DeferredResult#setResult}方法结束阻塞
     * @param resultMapper       查询返回结果映射函数
     * @param <T>                业务数据对象实例类型
     * @param <R>                接口响应对象类型
     * @return {@link DeferredResult}
     */
    public <T, R> DeferredResult<MessageResponse> process(String key,
                                                          long timeout,
                                                          String messageId,
                                                          Function<String, List<T>> queryFunction,
                                                          Predicate<List<T>> dataReadyPredicate,
                                                          Function<List<T>, R> resultMapper) {

        DeferredResult<MessageResponse> deferredResult = new DeferredResult<>(timeout);
        waitingMap.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).add(deferredResult);

        deferredResult.onTimeout(() -> deferredResult.setResult(MessageResponse.success(messageId, resultMapper.apply(Collections.emptyList()))));
        deferredResult.onCompletion(() -> waitingMap.getOrDefault(key, List.of()).remove(deferredResult));

        // If timeout<=0, directly return the queryFunction execution result
        if (timeout <= 0) {
            deferredResult.setResult(MessageResponse.success(messageId, resultMapper.apply(queryFunction.apply(key))));
        }

        long[] elapsed = {0};

        scheduler.scheduleAtFixedRate(() -> {
            if (deferredResult.isSetOrExpired()) return;

            List<T> data = queryFunction.apply(key);
            if (dataReadyPredicate.test(data)) {
                deferredResult.setResult(MessageResponse.success(messageId, resultMapper.apply(data)));
            }

            elapsed[0] += RETRY_PULL_INTERVAL;
            if (elapsed[0] >= timeout) {
                log.debug("Deferred query expired for key: {}", key);
            }

        }, 0, RETRY_PULL_INTERVAL, TimeUnit.MILLISECONDS);

        return deferredResult;
    }
}
