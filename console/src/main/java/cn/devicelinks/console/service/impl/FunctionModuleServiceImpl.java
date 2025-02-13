package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.model.StatusCodeConstants;
import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.common.utils.UUIDUtils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.ConditionGroup;
import cn.devicelinks.framework.jdbc.core.sql.operator.SqlFederationAway;
import cn.devicelinks.framework.jdbc.repositorys.FunctionModuleRepository;
import cn.devicelinks.framework.jdbc.repositorys.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TFunctionModule.FUNCTION_MODULE;

/**
 * 功能模块业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class FunctionModuleServiceImpl extends BaseServiceImpl<FunctionModule, String, FunctionModuleRepository> implements FunctionModuleService {
    @Autowired
    private ProductRepository productRepository;

    public FunctionModuleServiceImpl(FunctionModuleRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<FunctionModule> getFunctionModulesByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        return this.repository.selectByPage(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }

    @Override
    public FunctionModule addFunctionModule(FunctionModule functionModule) {
        // @formatter:off
        FunctionModule stored = this.repository.selectOne(
                ConditionGroup.withCondition(FUNCTION_MODULE.PRODUCT_ID.eq(functionModule.getProductId())),
                ConditionGroup.withCondition(
                        SqlFederationAway.AND,
                        SqlFederationAway.OR,
                        FUNCTION_MODULE.NAME.eq(functionModule.getName()),
                        FUNCTION_MODULE.IDENTIFIER.eq(functionModule.getIdentifier())));
        // @formatter:on
        if (stored != null) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_ALREADY_EXISTS);
        }
        Product product = productRepository.selectOne(functionModule.getProductId());
        if (product == null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, functionModule.getProductId());
        }
        functionModule.setId(UUIDUtils.generateNoDelimiter());
        this.repository.insert(functionModule);
        return functionModule;
    }

    @Override
    public FunctionModule updateFunctionModule(FunctionModule functionModule) {
        // @formatter:off
        FunctionModule stored = this.repository.selectOne(ConditionGroup.withCondition(
                        FUNCTION_MODULE.PRODUCT_ID.eq(functionModule.getProductId()),
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
        return functionModule;
    }

    @Override
    public void deleteFunctionModule(String functionModuleId) {
        this.repository.update(List.of(FUNCTION_MODULE.DELETED.set(true)), FUNCTION_MODULE.ID.eq(functionModuleId));
    }
}
