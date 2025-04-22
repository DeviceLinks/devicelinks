package cn.devicelinks.service.device;

import cn.devicelinks.entity.DeviceTag;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.repository.DeviceTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDeviceTag.DEVICE_TAG;

/**
 * 设备标签业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceTagServiceImpl extends BaseServiceImpl<DeviceTag, String, DeviceTagRepository> implements DeviceTagService {
    public DeviceTagServiceImpl(DeviceTagRepository repository) {
        super(repository);
    }

    @Override
    public List<DeviceTag> selectDeviceTagList(List<SearchFieldCondition> searchFieldConditionList) {
        return this.repository.selectDeviceTagList(searchFieldConditionList);
    }

    @Override
    public DeviceTag addDeviceTag(DeviceTag deviceTag) {
        DeviceTag storedDeviceTag = this.repository.selectOne(DEVICE_TAG.NAME.eq(deviceTag.getName()), DEVICE_TAG.DELETED.eq(Boolean.FALSE));
        if (storedDeviceTag != null) {
            return storedDeviceTag;
        }
        this.repository.insert(deviceTag);
        return deviceTag;
    }

    @Override
    public DeviceTag deleteDeviceTag(String tagId) {
        DeviceTag deviceTag = this.repository.selectOne(tagId);
        if (deviceTag != null) {
            deviceTag.setDeleted(Boolean.TRUE);
            this.repository.update(deviceTag);
        }
        return deviceTag;
    }
}
