package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DeviceAttributeDesiredService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.DeviceAttributeDesired;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeDesiredRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 设备期望属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceAttributeDesiredServiceImpl extends BaseServiceImpl<DeviceAttributeDesired, String, DeviceAttributeDesiredRepository>
        implements DeviceAttributeDesiredService {
    public DeviceAttributeDesiredServiceImpl(DeviceAttributeDesiredRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceAttributeDesired> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        return this.repository.getByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }
}
