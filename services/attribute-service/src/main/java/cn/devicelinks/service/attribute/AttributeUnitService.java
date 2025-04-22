package cn.devicelinks.service.attribute;

import cn.devicelinks.entity.AttributeUnit;
import cn.devicelinks.jdbc.BaseService;

import java.util.List;

/**
 * 属性单位业务逻辑接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface AttributeUnitService extends BaseService<AttributeUnit, String> {
    /**
     * 查询全部有效的属性单位
     *
     * @return 属性单位 {@link AttributeUnit}
     */
    List<AttributeUnit> selectEfficientList();
}
