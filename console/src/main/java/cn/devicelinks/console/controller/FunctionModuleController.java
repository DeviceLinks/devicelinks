package cn.devicelinks.console.controller;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.service.product.FunctionModuleService;
import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.api.model.request.AddFunctionModuleRequest;
import cn.devicelinks.api.model.request.UpdateFunctionModuleRequest;
import cn.devicelinks.component.web.search.annotation.SearchModule;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.entity.FunctionModule;
import cn.devicelinks.entity.SysUser;
import cn.devicelinks.component.web.search.SearchFieldModuleIdentifier;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能模块接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/function-module")
@AllArgsConstructor
public class FunctionModuleController {

    private FunctionModuleService functionModuleService;

    /**
     * 获取功能模块列表
     *
     * <p>此方法通过提供的分页和搜索参数来获取功能模块列表。
     * 它将返回一个包含符合查询条件的功能模块的分页结果。</p>
     *
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     *                         包含用于搜索功能模块的字段和值。
     * @return {@link FunctionModule}
     * 包含符合查询条件的功能模块的分页结果。
     * @throws ApiException 如果在获取功能模块列表时发生错误。
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.FunctionModule)
    public ApiResponse<List<FunctionModule>> getFunctionModuleByPageable(@Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        List<FunctionModule> functionModuleList = this.functionModuleService.selectBySearchField(searchFieldQuery);
        return ApiResponse.success(functionModuleList);
    }

    /**
     * 获取功能模块详情
     * <p>
     * 此方法通过功能模块ID获取特定功能模块的详细信息。
     * 如果找不到对应的功能模块，将抛出ApiException异常。
     *
     * @param moduleId 功能模块ID {@link FunctionModule#getId()}
     *                 必须是有效的字符串，最大长度为32个字符
     * @return {@link ApiResponse} 对象，包含找到的 {@link FunctionModule} 实例
     * @throws ApiException 如果未找到指定ID的功能模块
     *                      异常将包含状态码 {@link StatusCodeConstants#FUNCTION_MODULE_NOT_FOUND}
     *                      以及moduleId作为附加信息
     */
    @GetMapping(value = "/{moduleId}")
    public ApiResponse<FunctionModule> getFunctionModuleById(@Valid @PathVariable("moduleId") @Length(max = 32) String moduleId) throws ApiException {
        FunctionModule module = this.functionModuleService.selectById(moduleId);
        if (ObjectUtils.isEmpty(module)) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, moduleId);
        }
        return ApiResponse.success(module);
    }

    /**
     * 添加功能模块
     *
     * @param request 包含创建新功能模块所需的必要信息的请求对象。
     *                预期的请求对象是 {@link AddFunctionModuleRequest} 的有效实例。
     * @return 包含新创建的功能模块实例的 {@link ApiResponse} 对象。
     * 预期的功能模块实例是 {@link FunctionModule} 类型。
     * @throws ApiException 如果在添加功能模块时发生错误。
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.FunctionModule,
            objectId = "{#executionSucceed ? #result.data.id : #p0.name}",
            msg = "{#executionSucceed ? '添加功能模块成功' : '添加功能模块失败'}",
            activateData = "{#p0}")
    public ApiResponse<FunctionModule> addFunctionModule(@Valid @RequestBody AddFunctionModuleRequest request) throws ApiException {
        SysUser currentUser = UserDetailsContext.getCurrentUser();
        // @formatter:off
        FunctionModule module = new FunctionModule()
                .setProductId(request.getProductId())
                .setName(request.getName())
                .setIdentifier(request.getIdentifier())
                .setCreateBy(currentUser.getId());
        // @formatter:on
        module = this.functionModuleService.addFunctionModule(module);
        return ApiResponse.success(module);
    }

    /**
     * 更新功能模块
     *
     * @param moduleId 功能模块ID {@link FunctionModule#getId()}
     * @param request  包含更新功能模块所需信息的请求对象。
     *                 预期的请求对象是 {@link UpdateFunctionModuleRequest} 的有效实例。
     * @return 包含已更新的功能模块的 {@link ApiResponse} 对象。
     * 预期的返回类型是 {@link FunctionModule}。
     * @throws ApiException 如果在更新功能模块时发生错误。
     */
    @PostMapping(value = "/{moduleId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.FunctionModule,
            objectId = "{#p0}",
            object = "{@functionModuleServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '更新功能模块成功' : '更新功能模块失败'}",
            activateData = "{#p1}")
    public ApiResponse<FunctionModule> updateFunctionModule(@Valid @PathVariable("moduleId") @Length(max = 32) String moduleId,
                                            @Valid @RequestBody UpdateFunctionModuleRequest request) throws ApiException {
        FunctionModule storedModule = this.functionModuleService.selectById(moduleId);
        if (ObjectUtils.isEmpty(storedModule)) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, moduleId);
        }

        storedModule.setName(request.getName())
                .setIdentifier(request.getIdentifier());

        storedModule = this.functionModuleService.updateFunctionModule(storedModule);
        return ApiResponse.success(storedModule);
    }

    /**
     * 删除功能模块
     *
     * @param moduleId 功能模块ID {@link FunctionModule#getId()}
     *                 预期是一个有效的字符串，最多32个字符。
     * @return 包含已删除的功能模块实例的 {@link ApiResponse} 对象。
     * 预期的返回类型是 {@link FunctionModule}。
     * @throws ApiException 如果在删除功能模块时发生错误。
     *                      该异常将包含状态码 {@link StatusCodeConstants#FUNCTION_MODULE_NOT_FOUND}
     *                      并且如果指定了模块ID，则将其作为附加信息。
     */
    @DeleteMapping(value = "/{moduleId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.FunctionModule,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '删除功能模块成功' : '删除功能模块失败'}",
            activateData = "{#executionSucceed ? #result.data : null}")
    public ApiResponse<FunctionModule> deleteFunctionModule(@Valid @PathVariable("moduleId") @Length(max = 32) String moduleId) throws ApiException {
        FunctionModule storedModule = this.functionModuleService.selectById(moduleId);
        if (ObjectUtils.isEmpty(storedModule)) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, moduleId);
        }
        this.functionModuleService.deleteFunctionModule(moduleId);
        return ApiResponse.success(storedModule);
    }
}
