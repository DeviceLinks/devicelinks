package cn.devicelinks.console.service;

import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.jdbc.BaseService;

import java.util.List;

/**
 * 功能模块业务逻辑接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface FunctionModuleService extends BaseService<FunctionModule, String> {
    /**
     * 获取功能模块列表
     *
     * @param searchFieldQuery 检索字段参数实体
     * @return {@link FunctionModule}
     */
    List<FunctionModule> selectBySearchField(SearchFieldQuery searchFieldQuery);

    /**
     * 添加产品的默认功能模块
     *
     * @param productId 产品ID {@link FunctionModule#getProductId()}
     * @return 已添加的默认功能模块 {@link FunctionModule}
     */
    FunctionModule addProductDefaultFunctionModule(String productId);

    /**
     * 添加功能模块
     *
     * @param functionModule 功能模块 {@link FunctionModule}
     * @return 已经添加的功能模块 {@link FunctionModule}
     */
    FunctionModule addFunctionModule(FunctionModule functionModule);

    /**
     * 更新功能模块
     *
     * @param functionModule 功能模块 {@link FunctionModule}
     * @return 已更新的功能模块 {@link FunctionModule}
     */
    FunctionModule updateFunctionModule(FunctionModule functionModule);

    /**
     * 逻辑删除功能模块
     *
     * @param functionModuleId 功能模块ID {@link FunctionModule#getId()}
     */
    void deleteFunctionModule(String functionModuleId);

    /**
     * 查询产品定义的功能模块列表
     *
     * @param productId 产品ID {@link FunctionModule#getProductId()}
     * @return {@link FunctionModule}
     */
    List<FunctionModule> getProductFunctionModule(String productId);

    /**
     * 根据标识符查询功能模块
     *
     * @param identifier 功能模块标识符 {@link FunctionModule#getIdentifier()}
     * @return {@link FunctionModule}
     */
    FunctionModule selectByIdentifier(String identifier);
}
