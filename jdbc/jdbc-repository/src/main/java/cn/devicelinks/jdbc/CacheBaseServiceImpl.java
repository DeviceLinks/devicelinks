package cn.devicelinks.jdbc;

import cn.devicelinks.common.Constants;
import cn.devicelinks.common.exception.DeviceLinksException;
import cn.devicelinks.component.cache.core.CacheKey;
import cn.devicelinks.component.cache.core.CacheSupport;
import cn.devicelinks.jdbc.core.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 支持缓存的{@link BaseServiceImpl}实现
 *
 * @author 恒宇少年
 * @since 1.0
 */
public abstract class CacheBaseServiceImpl<T extends Serializable, PK, R extends Repository<T, PK>, K extends CacheKey, E>
        implements BaseService<T, PK>, ApplicationEventPublisherAware {

    private static final String PK_FIELD_NAME = "id";
    private static final String CACHE_KEY_ID_SET_METHOD = "setId";

    protected R repository;
    protected ApplicationEventPublisher eventPublisher;
    @Autowired
    protected CacheSupport<K, T> cache;

    public CacheBaseServiceImpl(R repository) {
        this.repository = repository;
    }

    protected void publishCacheEvictEvent(E event) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            eventPublisher.publishEvent(event);
        } else {
            handleCacheEvictEvent(event);
        }
    }

    public abstract void handleCacheEvictEvent(E event);

    @Override
    public int insert(T object) {
        return repository.insert(object);
    }

    @Override
    public int deleteById(PK pk) {
        int deleteCount = repository.delete(pk);
        if (deleteCount > Constants.ZERO) {
            K cacheKey = buildPkCacheKey(pk);
            cache.evict(cacheKey);
        }
        return deleteCount;
    }

    @Override
    public int update(T object) {
        int updateCount = repository.update(object);
        if (updateCount > Constants.ZERO) {
            try {
                Field idField = object.getClass().getDeclaredField(PK_FIELD_NAME);
                idField.setAccessible(true);
                PK pk = (PK) idField.get(object);
                K cacheKey = buildPkCacheKey(pk);
                cache.evict(cacheKey);
            } catch (Exception e) {
                throw new DeviceLinksException("Exception encountered while clearing cache.", e);
            }
        }
        return updateCount;
    }

    @Override
    public T selectById(PK pk) {
        if (pk == null) {
            return null;
        }
        K cacheKey = buildPkCacheKey(pk);
        return cache.get(cacheKey, () -> repository.selectOne(pk));
    }

    @Override
    public List<T> selectAll() {
        return repository.select();
    }

    @SuppressWarnings("unchecked")
    protected K buildPkCacheKey(PK pk) {
        try {
            Class<?> clazz = (Class<?>) ((java.lang.reflect.ParameterizedType)
                    this.getClass().getGenericSuperclass()).getActualTypeArguments()[3];

            K cacheKey = (K) clazz.getDeclaredConstructor().newInstance();
            Method setIdMethod = clazz.getMethod(CACHE_KEY_ID_SET_METHOD, pk.getClass());
            setIdMethod.invoke(cacheKey, pk);
            return cacheKey;
        } catch (Exception e) {
            throw new DeviceLinksException("Failed to construct PK CacheKey.", e);
        }
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
