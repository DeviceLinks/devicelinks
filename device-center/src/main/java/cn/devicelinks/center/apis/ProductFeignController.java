package cn.devicelinks.center.apis;

import cn.devicelinks.api.device.center.ProductFeignClient;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.entity.Product;
import cn.devicelinks.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品Feign接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/products")
@AllArgsConstructor
public class ProductFeignController implements ProductFeignClient {

    private final ProductService productService;

    @Override
    @GetMapping
    public ApiResponse<Product> getByProductKey(String productKey) {
        return ApiResponse.success(productService.selectByKey(productKey));
    }

    @Override
    @GetMapping("/{productId}")
    public ApiResponse<Product> getByProductId(@PathVariable("productId") String productId) {
        return ApiResponse.success(productService.selectById(productId));
    }
}
