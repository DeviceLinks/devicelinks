package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.DataChartFieldService;
import cn.devicelinks.framework.common.pojos.DataChartField;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.DataChartFieldRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 数据图表字段业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class DataChartFieldServiceImpl extends BaseServiceImpl<DataChartField, String, DataChartFieldRepository> implements DataChartFieldService {
    public DataChartFieldServiceImpl(DataChartFieldRepository repository) {
        super(repository);
    }
}
