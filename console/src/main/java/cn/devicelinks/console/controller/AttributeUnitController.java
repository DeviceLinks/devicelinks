package cn.devicelinks.console.controller;

import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.entity.AttributeUnit;
import cn.devicelinks.service.attribute.AttributeUnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 属性单位接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/attribute/unit")
@AllArgsConstructor
public class AttributeUnitController {

    private AttributeUnitService attributeUnitService;

    /**
     * 获取有效的属性单位列表。
     *
     * @return ApiResponse<List < AttributeUnit>> 包含有效属性单位列表的响应对象
     * @throws ApiException 如果在获取数据过程中发生错误，则抛出此异常
     */
    @GetMapping
    public ApiResponse<List<AttributeUnit>> getEfficientList() throws ApiException {
        List<AttributeUnit> attributeUnitList = this.attributeUnitService.selectEfficientList();
        return ApiResponse.success(attributeUnitList);
    }
}
