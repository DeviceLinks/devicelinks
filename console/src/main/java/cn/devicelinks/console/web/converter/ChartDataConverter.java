package cn.devicelinks.console.web.converter;

import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import cn.devicelinks.framework.jdbc.model.dto.ChartDataDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据图表实体转换类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface ChartDataConverter {
    /**
     * get new mapStruct instance
     */
    ChartDataConverter INSTANCE = Mappers.getMapper(ChartDataConverter.class);

    ChartDataDTO fromChartDataConfig(ChartDataConfig config);
}
