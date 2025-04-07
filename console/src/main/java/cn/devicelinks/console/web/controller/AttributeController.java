package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.web.request.AddAttributeRequest;
import cn.devicelinks.console.web.request.UpdateAttributeRequest;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.service.AttributeService;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

/**
 * 属性接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/attribute")
@AllArgsConstructor
public class AttributeController {

    private AttributeService attributeService;

    /**
     * 获取属性列表，支持分页和搜索功能。
     * <p>
     * 此方法根据提供的分页查询参数和搜索字段查询参数，返回符合条件的属性列表。
     *
     * @param paginationQuery  分页查询参数，包含当前页码和每页大小 {@link PaginationQuery}
     * @param searchFieldQuery 搜索字段查询参数，包含用于过滤属性的字段 {@link SearchFieldQuery}
     * @return 返回成功响应 {@link ApiResponse}，其中包含符合条件的属性列表。
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败或参数验证失败。
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.Attribute)
    public ApiResponse<PageResult<Attribute>> getAttributeByPageable(@Valid PaginationQuery paginationQuery,
                                              @Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        PageResult<Attribute> pageResult = this.attributeService.getAttributesByPage(paginationQuery, searchFieldQuery);
        return ApiResponse.success(pageResult);
    }

    /**
     * 获取属性详情
     * <p>
     * 此方法通过属性 ID 获取特定属性的详细信息。
     * 如果找不到对应的属性，将抛出ApiException 异常。
     *
     * @param attributeId 属性 ID {@link Attribute#getId()}
     * @return 返回成功响应 {@link Attribute}，其中包含属性的详细信息。
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败或参数验证失败。
     */
    @GetMapping(value = "/{attributeId}")
    public ApiResponse<AttributeDTO> getAttribute(@PathVariable("attributeId") String attributeId) throws ApiException {
        return ApiResponse.success(this.attributeService.getAttributeById(attributeId));
    }

    /**
     * 新增属性
     *
     * @param request 新增属性请求实体参数 {@link AddAttributeRequest}
     * @return 返回成功响应 {@link Attribute}，其中包含新创建的属性实例。
     * @throws ApiException 如果在处理请求时发生错误，例如参数验证失败。
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.Attribute,
            objectId = "{#executionSucceed? #result.data.id : #p0.name}",
            msg = "{#executionSucceed? '属性添加成功' : '属性添加失败'}",
            activateData = "{#p0}")
    public ApiResponse<AttributeDTO> addAttribute(@Valid @RequestBody AddAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.attributeService.addAttribute(request));
    }

    /**
     * 更新属性
     *
     * @param attributeId 属性 ID {@link Attribute#getId()}
     * @param request     更新属性请求实体参数 {@link UpdateAttributeRequest}
     * @return 返回成功响应 {@link ApiResponse}，其中包含更新操作的结果。
     * @throws ApiException 如果在处理请求时发生错误，例如参数验证失败。
     */
    @PostMapping(value = "/{attributeId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.Attribute,
            objectId = "{#p0}",
            object = "{@attributeServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed? '属性更新成功' : '属性更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<AttributeDTO> updateAttribute(@PathVariable("attributeId") @NotEmpty @Length(max = 32) String attributeId,
                                       @Valid @RequestBody UpdateAttributeRequest request) throws ApiException {
        return ApiResponse.success(this.attributeService.updateAttribute(attributeId, request));
    }

    /**
     * 删除属性
     * <p>
     * 此方法通过属性 ID 删除特定属性。
     *
     * @param attributeId 属性 ID {@link Attribute#getId()}
     * @return 返回成功响应 {@link ApiResponse}，其中包含删除操作的结果。
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败或参数验证失败。
     */
    @DeleteMapping(value = "/{attributeId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.Attribute,
            objectId = "{#p0}",
            msg = "{#executionSucceed? '属性删除成功' : '属性删除失败'}",
            activateData = "{#executionSucceed ? #result.data : null}")
    public ApiResponse<Attribute> deleteAttribute(@Length(max = 32) @PathVariable String attributeId) throws ApiException {
        return ApiResponse.success(this.attributeService.deleteAttribute(attributeId));
    }
}