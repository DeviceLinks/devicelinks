package cn.devicelinks.api.model.converter;

import cn.devicelinks.api.model.dto.DataChartDTO;
import cn.devicelinks.framework.common.pojos.DataChart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据图表实体转换类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface DataChartConverter {
    /**
     * get new mapStruct instance
     */
    DataChartConverter INSTANCE = Mappers.getMapper(DataChartConverter.class);

    DataChartDTO fromDataChart(DataChart chart);
}
