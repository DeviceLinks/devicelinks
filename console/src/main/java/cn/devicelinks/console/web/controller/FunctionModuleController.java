package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.FunctionModuleService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddFunctionModuleRequest;
import cn.devicelinks.console.web.request.UpdateFunctionModuleRequest;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.FunctionModule;
import cn.devicelinks.framework.common.pojos.SysUser;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 功能模块接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/function/module")
@AllArgsConstructor
public class FunctionModuleController {

    private FunctionModuleService functionModuleService;

    /**
     * 获取功能模块列表
     *
     * <p>此方法通过提供的分页和搜索参数来获取功能模块列表。
     * 它将返回一个包含符合查询条件的功能模块的分页结果。</p>
     *
     * @param paginationQuery  分页查询参数 {@link PaginationQuery}
     *                         包含分页信息，如页码和每页记录数。
     * @param searchFieldQuery 搜索字段查询参数 {@link SearchFieldQuery}
     *                         包含用于搜索功能模块的字段和值。
     * @return {@link cn.devicelinks.framework.common.pojos.FunctionModule}
     * 包含符合查询条件的功能模块的分页结果。
     * @throws ApiException 如果在获取功能模块列表时发生错误。
     */
    @PostMapping(value = "/filter")
    public ApiResponse getFunctionModuleByPageable(@Valid PaginationQuery paginationQuery,
                                                   @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        PageResult<FunctionModule> pageResult = this.functionModuleService.getFunctionModulesByPage(paginationQuery, searchFieldQuery);
        return ApiResponse.success(pageResult);
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
    public ApiResponse getFunctionModuleById(@Valid @PathVariable("moduleId") @Length(max = 32) String moduleId) throws ApiException {
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
    public ApiResponse addFunctionModule(@Valid @RequestBody AddFunctionModuleRequest request) throws ApiException {
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
    public ApiResponse updateFunctionModule(@Valid @PathVariable("moduleId") @Length(max = 32) String moduleId,
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
    public ApiResponse deleteFunctionModule(@Valid @PathVariable("moduleId") @Length(max = 32) String moduleId) throws ApiException {
        FunctionModule storedModule = this.functionModuleService.selectById(moduleId);
        if (ObjectUtils.isEmpty(storedModule)) {
            throw new ApiException(StatusCodeConstants.FUNCTION_MODULE_NOT_FOUND, moduleId);
        }
        this.functionModuleService.deleteFunctionModule(moduleId);
        return ApiResponse.success(storedModule);
    }
}
