package cn.devicelinks.jdbc.repository;

import cn.devicelinks.api.model.dto.DeviceAttributeDTO;
import cn.devicelinks.api.model.dto.DeviceAttributeLatestDTO;
import cn.devicelinks.entity.Attribute;
import cn.devicelinks.entity.DeviceAttribute;
import cn.devicelinks.jdbc.core.Repository;
import cn.devicelinks.jdbc.core.page.PageQuery;
import cn.devicelinks.jdbc.core.page.PageResult;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.core.sql.SortCondition;

import java.util.List;

/**
 * 设备属性数据接口定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface DeviceAttributeRepository extends Repository<DeviceAttribute, String> {
    /**
     * 分页获取设备属性
     *
     * @param searchFieldConditionList 检索字段查询条件列表
     * @param pageQuery                分页查询对象
     * @param sortCondition            排序条件
     * @return 设备属性分页对象
     */
    PageResult<DeviceAttributeDTO> getByPageable(List<SearchFieldCondition> searchFieldConditionList, PageQuery pageQuery, SortCondition sortCondition);

    /**
     * 获取设备属性最新值
     *
     * @param deviceId            设备ID {@link DeviceAttribute#getDeviceId()}
     * @param moduleId            属性所属功能模块ID {@link DeviceAttribute#getModuleId()}
     * @param attributeName       属性名称 {@link Attribute#getName()}
     * @param attributeIdentifier 属性标识符 {@link Attribute#getIdentifier()}
     * @return {@link DeviceAttributeLatestDTO}
     */
    List<DeviceAttributeLatestDTO> getLatestAttribute(String deviceId, String moduleId, String attributeName, String attributeIdentifier);

    /**
     * 查询设备属性列表
     *
     * @param deviceId    设备ID {@link DeviceAttribute#getDeviceId()}
     * @param identifiers 属性标识符列表 {@link DeviceAttribute#getIdentifier()}
     * @return 设备属性 {@link DeviceAttribute}
     */
    List<DeviceAttribute> selectDeviceAttributes(String deviceId, String[] identifiers);
}
