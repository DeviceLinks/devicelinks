package cn.devicelinks.service.device;

import cn.devicelinks.entity.DataChartField;
import cn.devicelinks.jdbc.BaseServiceImpl;
import cn.devicelinks.jdbc.repository.DataChartFieldRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.devicelinks.jdbc.tables.TDataChartField.DATA_CHART_FIELD;

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

    @Override
    public List<DataChartField> getFieldListByChartId(String chartId) {
        return this.repository.select(DATA_CHART_FIELD.CHART_ID.eq(chartId));
    }
}
