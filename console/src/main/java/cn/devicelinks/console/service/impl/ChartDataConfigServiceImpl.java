package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.service.ChartDataConfigService;
import cn.devicelinks.console.service.ChartDataFieldsService;
import cn.devicelinks.console.web.converter.ChartDataConverter;
import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.common.pojos.ChartDataFields;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.model.dto.ChartDataDTO;
import cn.devicelinks.framework.jdbc.repositorys.ChartDataConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 数据图表配置业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class ChartDataConfigServiceImpl extends BaseServiceImpl<ChartDataConfig, String, ChartDataConfigRepository> implements ChartDataConfigService {

    @Autowired
    private ChartDataFieldsService chartDataFieldsService;

    public ChartDataConfigServiceImpl(ChartDataConfigRepository repository) {
        super(repository);
    }

    @Override
    public ChartDataDTO addChartData(ChartDataConfig chartDataConfig, List<ChartDataFields> fields) {
        Assert.notNull(chartDataConfig, "数据图表对象实例为空，无法添加.");
        Assert.notEmpty(fields, "数据图表需要至少包含一个字段.");
        this.repository.insert(chartDataConfig);
        for (ChartDataFields field : fields) {
            field.setConfigId(chartDataConfig.getId());
            this.chartDataFieldsService.insert(field);
        }
        ChartDataDTO chartDataDTO = ChartDataConverter.INSTANCE.fromChartDataConfig(chartDataConfig);
        chartDataDTO.setFields(fields);
        return chartDataDTO;
    }
}
