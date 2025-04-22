package cn.devicelinks.service.device;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.framework.jdbc.PaginationQueryConverter;
import cn.devicelinks.framework.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.framework.common.web.search.SearchFieldQuery;
import cn.devicelinks.api.model.request.AddDeviceTelemetryRequest;
import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.TelemetryMetricType;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Device;
import cn.devicelinks.framework.common.pojos.DeviceTelemetry;
import cn.devicelinks.framework.common.pojos.TelemetryAddition;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.repositorys.DeviceTelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TDeviceTelemetry.DEVICE_TELEMETRY;

/**
 * 遥测数据业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
public class DeviceTelemetryServiceImpl extends BaseServiceImpl<DeviceTelemetry, String, DeviceTelemetryRepository> implements DeviceTelemetryService {
    @Autowired
    private DeviceService deviceService;

    public DeviceTelemetryServiceImpl(DeviceTelemetryRepository repository) {
        super(repository);
    }

    @Override
    public PageResult<DeviceTelemetry> getTelemetryByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getTelemetryByPage(searchFieldConditionList,
                converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public DeviceTelemetry addTelemetry(String deviceId, AddDeviceTelemetryRequest request) {
        Device device = this.deviceService.selectById(deviceId);
        if (device == null || device.isDeleted()) {
            throw new ApiException(StatusCodeConstants.DEVICE_NOT_EXISTS, deviceId);
        }
        // @formatter:off
        DeviceTelemetry telemetry = this.repository.selectOne(
                DEVICE_TELEMETRY.DEVICE_ID.eq(deviceId),
                DEVICE_TELEMETRY.METRIC_KEY.eq(request.getIdentifier()),
                DEVICE_TELEMETRY.DELETED.eq(Boolean.FALSE)
        );
        if (telemetry != null) {
            telemetry
                    .setMetricValue(request.getValue())
                    .setAddition(this.setTelemetryDataType(telemetry.getAddition(), AttributeDataType.valueOf(request.getDataType())))
                    .setLastUpdateTimestamp(System.currentTimeMillis());
            this.repository.update(telemetry);
        } else {
            telemetry = new DeviceTelemetry()
                    .setDeviceId(deviceId)
                    .setMetricKey(request.getIdentifier())
                    .setMetricType(TelemetryMetricType.BusinessData)
                    .setMetricValue(request.getValue())
                    .setAddition(this.setTelemetryDataType(null, AttributeDataType.valueOf(request.getDataType())))
                    .setLastUpdateTimestamp(System.currentTimeMillis());
            this.repository.insert(telemetry);
        }
        // @formatter:on
        return telemetry;
    }

    @Override
    public DeviceTelemetry deleteTelemetry(String deviceId, String telemetryId) {
        DeviceTelemetry telemetry = this.repository.selectOne(
                DEVICE_TELEMETRY.DEVICE_ID.eq(deviceId),
                DEVICE_TELEMETRY.ID.eq(telemetryId),
                DEVICE_TELEMETRY.DELETED.eq(Boolean.FALSE));
        if (telemetry == null) {
            throw new ApiException(StatusCodeConstants.TELEMETRY_NOT_EXISTS, telemetryId);
        }
        telemetry.setDeleted(Boolean.TRUE);
        this.repository.update(telemetry);
        return telemetry;
    }

    @Override
    public DeviceTelemetry checkTelemetryIdChartField(String deviceId, String telemetryId) {
        DeviceTelemetry telemetry = this.repository.selectOne(
                DEVICE_TELEMETRY.DEVICE_ID.eq(deviceId),
                DEVICE_TELEMETRY.ID.eq(telemetryId),
                DEVICE_TELEMETRY.DELETED.eq(Boolean.FALSE));
        if (telemetry == null) {
            throw new ApiException(StatusCodeConstants.TELEMETRY_NOT_EXISTS, telemetryId);
        }
        // Whether the telemetry data type supports adding to the chart
        AttributeDataType telemetryDataType = this.getTelemetryDataType(telemetry.getAddition());
        if (AttributeDataType.INTEGER != telemetryDataType && AttributeDataType.DOUBLE != telemetryDataType) {
            throw new ApiException(StatusCodeConstants.TELEMETRY_DATA_TYPE_CANNOT_ADD_CHART, telemetry.getMetricKey());
        }
        return telemetry;
    }

    private TelemetryAddition setTelemetryDataType(TelemetryAddition telemetryAddition, AttributeDataType dataType) {
        telemetryAddition = telemetryAddition == null ? new TelemetryAddition() : telemetryAddition;
        TelemetryAddition.TelemetryMetadata telemetryMetadata =
                telemetryAddition.getMetadata() == null ? new TelemetryAddition.TelemetryMetadata() : telemetryAddition.getMetadata();
        telemetryMetadata.setDataType(dataType);
        telemetryAddition.setMetadata(telemetryMetadata);
        return telemetryAddition;
    }

    private AttributeDataType getTelemetryDataType(TelemetryAddition telemetryAddition) {
        telemetryAddition = telemetryAddition == null ? new TelemetryAddition() : telemetryAddition;
        TelemetryAddition.TelemetryMetadata telemetryMetadata =
                telemetryAddition.getMetadata() == null ? new TelemetryAddition.TelemetryMetadata() : telemetryAddition.getMetadata();
        return telemetryMetadata.getDataType();
    }
}
