package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.service.ProductService;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.web.request.AddProductRequest;
import cn.devicelinks.console.web.request.UpdateProductRequest;
import cn.devicelinks.console.web.response.RegenerateKeySecretResponse;
import cn.devicelinks.console.web.search.SearchModule;
import cn.devicelinks.framework.common.*;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

/**
 * 产品接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 获取分页产品列表
     * <p>
     * 该接口允许获取分页的产品列表。结果可以根据提供的搜索条件进行过滤和排序。
     *
     * @param paginationQuery  分页查询参数，包括页码、每页大小和排序信息。必须是有效的 {@link PaginationQuery} 对象。
     * @param searchFieldQuery 用于过滤产品的搜索条件。可以包含各种特定字段的搜索参数。必须是有效的 {@link SearchFieldQuery} 对象。
     * @return 返回一个 {@link ApiResponse} 对象，包含符合给定条件的分页产品列表。
     * 响应中包括分页元数据和产品列表。
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.Product)
    public ApiResponse<PageResult<Product>> getProductByPageable(@Valid PaginationQuery paginationQuery, @Valid @RequestBody SearchFieldQuery searchFieldQuery) {
        return ApiResponse.success(this.productService.getPageByPageable(paginationQuery, searchFieldQuery));
    }

    /**
     * 获取产品信息
     * <p>
     * 该接口用于根据产品ID获取指定产品的详细信息。产品ID必须为非空字符串，且长度不超过32个字符。
     *
     * @param productId 需要查询的产品ID。必须为非空字符串，且长度不超过32个字符。
     * @return 返回一个 {@link ApiResponse} 对象，包含指定产品的详细信息。如果产品不存在，响应中可能包含错误信息。
     * @throws ApiException 如果请求参数无效或产品查询过程中发生错误，将抛出 {@link ApiException} 异常。
     */
    @GetMapping(value = "/{productId}")
    public ApiResponse<Product> getProductInfo(@PathVariable @NotEmpty @Length(max = 32) String productId) throws ApiException {
        Product product = this.productService.selectById(productId);
        if (product == null || product.isDeleted()) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, productId);
        }
        return ApiResponse.success(product);
    }

    /**
     * 新增产品
     * <p>
     * 该接口用于新增一个产品。请求体中需要包含产品的详细信息，系统会根据提供的信息创建新的产品记录。
     *
     * @param request 包含产品详细信息的请求对象。必须是有效的 {@link AddProductRequest} 对象。
     * @return 返回一个 {@link ApiResponse} 对象，表示操作是否成功。如果成功，响应中可能包含新创建的产品信息。
     * @throws ApiException 如果请求参数无效或产品创建过程中发生错误，将抛出 {@link ApiException} 异常。
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.Product,
            objectId = "{#executionSucceed ? #result.data.id : #p0.name}",
            msg = "{#executionSucceed? '添加产品成功' : '添加产品失败'}",
            activateData = "{#p0}")
    public ApiResponse<Product> addProduct(@Valid @RequestBody AddProductRequest request) throws ApiException {
        String currentUserId = UserDetailsContext.getUserId();
        // @formatter:off
        Product product = new Product()
                .setName(request.getName())
                .setDeviceType(DeviceType.valueOf(request.getDeviceType()))
                .setNetworkingAway(DeviceNetworkingAway.valueOf(request.getNetworkingAway()))
                .setAccessGatewayProtocol(AccessGatewayProtocol.valueOf(request.getAccessGatewayProtocol()))
                .setDataFormat(DataFormat.valueOf(request.getDataFormat()))
                .setAuthenticationMethod(DeviceAuthenticationMethod.valueOf(request.getAuthenticationMethod()))
                .setStatus(ProductStatus.Development)
                .setCreateBy(currentUserId)
                .setDescription(request.getDescription());
        // @formatter:on
        return ApiResponse.success(this.productService.addProduct(product));
    }

    /**
     * 更新产品信息
     * <p>
     * 该接口用于更新指定产品的信息。通过产品ID定位需要更新的产品，并使用请求体中的新数据替换原有信息。
     *
     * @param productId 需要更新的产品ID。必须为非空字符串，且长度不超过32个字符。
     * @param request   包含更新后产品信息的请求对象。必须是有效的 {@link UpdateProductRequest} 对象。
     * @return 返回一个 {@link ApiResponse} 对象，表示操作是否成功。如果成功，响应中可能包含更新后的产品信息。
     * @throws ApiException 如果请求参数无效或产品更新过程中发生错误，将抛出 {@link ApiException} 异常。
     */
    @PostMapping(value = "/{productId}")
    @OperationLog(action = LogAction.Update,
            objectType = LogObjectType.Product,
            objectId = "{#p0}",
            object = "{@productServiceImpl.selectById(#p0)}",
            msg = "{#executionSucceed ? '产品更新成功' : '产品更新失败'}",
            activateData = "{#p1}")
    public ApiResponse<Product> updateProduct(@PathVariable @NotEmpty @Length(max = 32) String productId,
                                     @Valid @RequestBody UpdateProductRequest request) throws ApiException {
        Product product = this.productService.selectById(productId);
        if (product == null) {
            throw new ApiException(StatusCodeConstants.PRODUCT_NOT_EXISTS, request.getName());
        }
        // @formatter:off
        product.setName(request.getName())
                .setDeviceType(DeviceType.valueOf(request.getDeviceType()))
                .setNetworkingAway(DeviceNetworkingAway.valueOf(request.getNetworkingAway()))
                .setAccessGatewayProtocol(AccessGatewayProtocol.valueOf(request.getAccessGatewayProtocol()))
                .setDataFormat(DataFormat.valueOf(request.getDataFormat()))
                .setAuthenticationMethod(DeviceAuthenticationMethod.valueOf(request.getAuthenticationMethod()))
                .setDescription(request.getDescription());
        // @formatter:on
        return ApiResponse.success(this.productService.updateProduct(product));
    }

    /**
     * 删除产品
     *
     * @param productId 需要删除的产品ID。必须为非空字符串，且长度不超过32个字符。
     * @return 已经删除的产品信息
     * @throws ApiException 如果请求参数无效或产品删除过程中发生错误，将抛出 {@link ApiException} 异常。
     */
    @DeleteMapping(value = "/{productId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.Product,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '产品删除成功' : '产品删除失败'}",
            activateData = "{#executionSucceed ? #result.data : null}")
    public ApiResponse<Product> deleteProduct(@PathVariable @NotEmpty @Length(max = 32) String productId) throws ApiException {
        return ApiResponse.success(this.productService.deleteProduct(productId));
    }

    /**
     * 发布产品
     * <p>
     * 该接口用于将指定的产品从开发状态发布为正式状态。发布后的产品将可以被用于生产环境。
     * 只有处于开发状态的产品才能被发布。发布操作是不可逆的，请谨慎操作。
     *
     * @param productId 需要发布的产品ID。必须为非空字符串，且长度不超过32个字符。
     * @return 返回一个 {@link ApiResponse} 对象，表示操作是否成功。
     * 如果成功，响应中可能包含已发布的产品信息。
     * @throws ApiException 如果请求参数无效、产品不存在、产品状态不允许发布，
     *                      或发布过程中发生其他错误，将抛出 {@link ApiException} 异常。
     */
    @PostMapping(value = "/{productId}/publish")
    @OperationLog(action = LogAction.Publish,
            objectType = LogObjectType.Product,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '产品发布成功' : '产品发布失败'}",
            activateData = "{#executionSucceed ? #result.data : null}")
    public ApiResponse<Product> publishProduct(@PathVariable @NotEmpty @Length(max = 32) String productId) throws ApiException {
        return ApiResponse.success(this.productService.publishProduct(productId));
    }

    /**
     * 重新生成产品的Key和Secret
     * <p>
     * 该接口用于重新生成指定产品的Key和Secret。这通常用于安全考虑或者当原有的Key和Secret可能已经泄露时。
     * 重新生成操作会使原有的Key和Secret失效，可能会影响使用旧凭证的设备连接，请谨慎操作。
     *
     * @return 返回一个 {@link ApiResponse} 对象，表示操作是否成功。
     * 如果成功，响应中可能包含新生成的Key和Secret信息。
     * @throws ApiException 如果重新生成过程中发生错误，例如产品不存在、没有权限或者系统错误等，
     *                      将抛出 {@link ApiException} 异常。
     */
    @PostMapping(value = "/{productId}/regenerate/key-secret")
    @OperationLog(action = LogAction.RegenerateKeySecret,
            objectType = LogObjectType.Product,
            objectId = "{#p0}",
            msg = "{#executionSucceed ? '重新生成Key-Secret成功' : '重新生成Key-Secret失败'}",
            activateData = "{#executionSucceed ? #result.data : null}")
    public ApiResponse<RegenerateKeySecretResponse> regenerateKeySecret(@PathVariable @NotEmpty @Length(max = 32) String productId) throws ApiException {
        return ApiResponse.success(this.productService.regenerateKeySecret(productId));
    }
}