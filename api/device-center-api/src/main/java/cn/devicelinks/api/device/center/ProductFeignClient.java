package cn.devicelinks.api.device.center;

import cn.devicelinks.component.openfeign.OpenFeignConstants;
import cn.devicelinks.component.openfeign.annotation.OpenFeignClient;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.Product;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 产品基本信息Feign客户端
 *
 * @author 恒宇少年
 * @since 1.0
 */
@OpenFeignClient(name = "devicelinks-device-center")
public interface ProductFeignClient {
    /**
     * 根据产品Key获取产品基本信息
     *
     * @param productKey {@link Product#getProductKey()}
     * @return {@link Product}
     */
    @RequestLine("GET /api/products?productKey={productKey}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Product> getByProductKey(@Param("productKey") String productKey);

    /**
     * 根据产品ID查询产品详情
     *
     * @param productId 产品ID {@link Product#getId()}
     * @return {@link Product}
     */
    @RequestLine("GET /api/products/{productId}")
    @Headers(OpenFeignConstants.JSON_CONTENT_TYPE_HEADER)
    ApiResponse<Product> getByProductId(@Param("productId") String productId);
}
