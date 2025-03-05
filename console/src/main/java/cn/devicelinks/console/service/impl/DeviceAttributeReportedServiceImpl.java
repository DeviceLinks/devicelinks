package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DeviceAttributeReportedService;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.framework.common.pojos.DeviceAttributeReported;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.model.dto.DeviceAttributeReportedDTO;
import cn.devicelinks.framework.jdbc.repositorys.DeviceAttributeReportedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 设备上报属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DeviceAttributeReportedServiceImpl extends BaseServiceImpl<DeviceAttributeReported, String, DeviceAttributeReportedRepository>
        implements DeviceAttributeReportedService {
    public DeviceAttributeReportedServiceImpl(DeviceAttributeReportedRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceAttributeReportedDTO> getByPageable(SearchFieldQuery searchFieldQuery, PaginationQuery paginationQuery) {
        return this.repository.getByPageable(searchFieldQuery.toSearchFieldConditionList(), paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }
}
