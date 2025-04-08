package cn.devicelinks.api.support.converter;

import cn.devicelinks.framework.common.pojos.DataChart;
import cn.devicelinks.framework.jdbc.model.dto.DataChartDTO;
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
