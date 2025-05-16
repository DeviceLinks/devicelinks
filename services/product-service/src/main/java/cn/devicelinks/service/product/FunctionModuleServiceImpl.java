package cn.devicelinks.service.product;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.entity.FunctionModule;
import cn.devicelinks.entity.Product;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.jdbc.cache.FunctionModuleCacheEvictEvent;
import cn.devicelinks.jdbc.cache.FunctionModuleCacheKey;
import cn.devicelinks.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.jdbc.repository.FunctionModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.devicelinks.jdbc.tables.TFunctionModule.FUNCTION_MODULE;

/**
 * 功能模块业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class FunctionModuleServiceImpl extends CacheBaseServiceImpl<FunctionModule, String, FunctionModuleRepository, FunctionModuleCacheKey, FunctionModuleCacheEvictEvent> implements FunctionModuleService {

    public static final String DEFAULT_FUNCTION_MODULE_NAME = "默认模块";

    public static final String DEFAULT_FUNCTION_MODULE_IDENTIFIER = "default";

    @Autowired
    private ProductService productService;

    public FunctionModuleServiceImpl(FunctionModuleRepository repository) {
        super(repository);
    }

    @Override
    @TransactionalEventListener(classes = FunctionModuleCacheEvictEvent.class)
    public void handleCacheEvictEvent(FunctionModuleCacheEvictEvent event) {
        FunctionModule savedFunctionModule = event.getSavedFunctionModule();
        if (savedFunctionModule != null) {
            cache.put(FunctionModuleCacheKey.builder().functionModuleId(savedFunctionModule.getId()).build(), savedFunctionModule);
            cache.put(FunctionModuleCacheKey.builder().identifier(savedFunctionModule.getIdentifier()).build(), savedFunctionModule);
        } else {
            List<FunctionModuleCacheKey> toEvict = new ArrayList<>();
            if (!ObjectUtils.isEmpty(event.getFunctionModuleId())) {
                toEvict.add(FunctionModuleCacheKey.builder().functionModuleId(event.getFunctionModuleId()).build());
            }
            if (!ObjectUtils.isEmpty(event.getIdentifier())) {
                toEvict.add(FunctionModuleCacheKey.builder().identifier(event.getIdentifier()).build());
            }
            cache.evict(toEvict);
        }
    }

    @Override
    public List<FunctionModule> selectBySearchField(SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        return this.repository.selectBySearchField(searchFieldConditionList);
    }

    @Override
    public FunctionModule addProductDefaultFunctionModule(String productId, UserAuthorizedAddition userAddition) {
        // @formatter:off
        FunctionModule defaultFunctionModule =
                new FunctionModule()
                        .setName(DEFAULT_FUNCTION_MODULE_NAME)
                        .setIdentifier(DEFAULT_FUNCTION_MODULE_IDENTIFIER)
                        .setProductId(productId)
                        .setCreateBy(userAddition.getUserId());
        // @formatter:on
        this.repository.insert(defaultFunctionModule);
        publishCacheEvictEvent(FunctionModuleCacheEvictEvent.builder().savedFunctionModule(defaultFunctionModule).build());
        return defaultFunctionModule;
    }

    @Override
    public FunctionModule addFunctionModule(FunctionModule functionModule) {
        // @formatter:off
        FunctionModule stored = this.repository.selectOne(
                ConditionGroup.withCondition(
                        FUNCTION_MODULE.PRODUCT_ID.eq(functionModule.getProductId()),
                        FUNCTION_MODULE.DELETED.eq(Boolean.FALSE)
                ),
                ConditionGroup.withCondition(
                        SqlFederationAway.AND,
                        SqlFederationAway.OR,
                        FUNCTION_MODULE.NAME.eq(functionModule.getName()),
                        FUNCTION_MODULE.IDENTIFIER.eq(functionModule.getIdentifier())));
        // @formatter:on
        if (stored != null) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_ALREADY_EXISTS);
        }
        Product product = productService.selectById(functionModule.getProductId());
        if (product == null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, functionModule.getProductId());
        }
        this.repository.insert(functionModule);
        publishCacheEvictEvent(FunctionModuleCacheEvictEvent.builder().savedFunctionModule(functionModule).build());
        return functionModule;
    }

    @Override
    public FunctionModule updateFunctionModule(FunctionModule functionModule) {
        // @formatter:off
        FunctionModule stored = this.repository.selectOne(ConditionGroup.withCondition(
                        FUNCTION_MODULE.PRODUCT_ID.eq(functionModule.getProductId()),
                        FUNCTION_MODULE.DELETED.eq(Boolean.FALSE),
                        FUNCTION_MODULE.ID.neq(functionModule.getId())
                ),
                ConditionGroup.withCondition(
                        SqlFederationAway.AND,
                        SqlFederationAway.OR,
                        FUNCTION_MODULE.NAME.eq(functionModule.getName()),
                        FUNCTION_MODULE.IDENTIFIER.eq(functionModule.getIdentifier())
                ));
        // @formatter:on
        if (stored != null) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_ALREADY_EXISTS);
        }
        this.repository.update(functionModule);
        publishCacheEvictEvent(FunctionModuleCacheEvictEvent.builder().functionModuleId(functionModule.getId()).identifier(functionModule.getIdentifier()).build());
        return functionModule;
    }

    @Override
    public void deleteFunctionModule(String functionModuleId) {
        FunctionModule functionModule = selectById(functionModuleId);
        if (functionModule == null || functionModule.isDeleted()) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, functionModuleId);
        }
        this.repository.update(List.of(FUNCTION_MODULE.DELETED.set(Boolean.TRUE)), FUNCTION_MODULE.ID.eq(functionModuleId));
        publishCacheEvictEvent(FunctionModuleCacheEvictEvent.builder().functionModuleId(functionModule.getId()).identifier(functionModule.getIdentifier()).build());
    }

    @Override
    public List<FunctionModule> getProductFunctionModule(String productId) {
        // @formatter:off
        return this.repository.select(
                FUNCTION_MODULE.CREATE_TIME.desc(),
                FUNCTION_MODULE.PRODUCT_ID.eq(productId),
                FUNCTION_MODULE.DELETED.eq(Boolean.FALSE)
        );
        // @formatter:on
    }

    @Override
    public FunctionModule selectByIdentifier(String identifier) {
        return cache.get(FunctionModuleCacheKey.builder().identifier(identifier).build(),
                () -> this.repository.selectOne(FUNCTION_MODULE.IDENTIFIER.eq(identifier)));
    }
}
