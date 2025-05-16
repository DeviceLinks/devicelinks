package cn.devicelinks.service.device;

import cn.devicelinks.entity.DeviceTag;
import cn.devicelinks.jdbc.CacheBaseServiceImpl;
import cn.devicelinks.jdbc.cache.DeviceTagCacheEvictEvent;
import cn.devicelinks.jdbc.cache.DeviceTagCacheKey;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.jdbc.repository.DeviceTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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
public class DeviceTagServiceImpl extends CacheBaseServiceImpl<DeviceTag, String, DeviceTagRepository, DeviceTagCacheKey, DeviceTagCacheEvictEvent> implements DeviceTagService {
    public DeviceTagServiceImpl(DeviceTagRepository repository) {
        super(repository);
    }

    @Override
    public void handleCacheEvictEvent(DeviceTagCacheEvictEvent event) {
        DeviceTag savedDeviceTag = event.getSavedDeviceTag();
        if (savedDeviceTag != null) {
            cache.put(DeviceTagCacheKey.builder().tagId(savedDeviceTag.getId()).build(), savedDeviceTag);
            cache.put(DeviceTagCacheKey.builder().name(savedDeviceTag.getName()).build(), savedDeviceTag);
        } else {
            List<DeviceTagCacheKey> toEvict = new ArrayList<>();
            if (!ObjectUtils.isEmpty(event.getTagId())) {
                toEvict.add(DeviceTagCacheKey.builder().tagId(event.getTagId()).build());
            }
            if (!ObjectUtils.isEmpty(event.getName())) {
                toEvict.add(DeviceTagCacheKey.builder().name(event.getName()).build());
            }
            cache.evict(toEvict);
        }
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
        publishCacheEvictEvent(DeviceTagCacheEvictEvent.builder().savedDeviceTag(deviceTag).build());
        return deviceTag;
    }

    @Override
    public DeviceTag deleteDeviceTag(String tagId) {
        DeviceTag deviceTag = selectById(tagId);
        if (deviceTag != null && !deviceTag.isDeleted()) {
            deviceTag.setDeleted(Boolean.TRUE);
            this.repository.update(deviceTag);
            publishCacheEvictEvent(DeviceTagCacheEvictEvent.builder().tagId(deviceTag.getId()).name(deviceTag.getName()).build());
        }
        return deviceTag;
    }
}
