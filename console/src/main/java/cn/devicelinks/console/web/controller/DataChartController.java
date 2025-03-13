package cn.devicelinks.console.web.controller;

import cn.devicelinks.console.service.ChartDataConfigService;
import cn.devicelinks.console.web.request.AddDataChartRequest;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import cn.devicelinks.framework.common.pojos.ChartDataConfig;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 数据图表接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/chart")
@AllArgsConstructor
public class DataChartController {

    private ChartDataConfigService chartDataConfigService;

    /**
     * 新增数据图表
     *
     * @param request 新增数据图表请求参数实体 {@link AddDataChartRequest}
     * @return 已添加的数据图表配置ID {@link ChartDataConfig#getId()}
     * @throws ApiException 新增过程中遇到的业务逻辑异常
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.Chart,
            objectId = "{#executionSucceed? #result.data.id : #p0.targetId}",
            msg = "{#executionSucceed? '新增数据图表成功' : '新增数据图表失败'}",
            activateData = "{#p0}")
    public ApiResponse addDataChart(@Valid @RequestBody AddDataChartRequest request) throws ApiException {
        return ApiResponse.success(this.chartDataConfigService.addChart(request));
    }

    /**
     * 删除数据图表
     *
     * @param chartId 数据图表ID {@link ChartDataConfig#getId()}
     * @return 已删除的数据图表 {@link ChartDataConfig}
     * @throws ApiException 删除过程中遇到的业务逻辑异常
     */
    @DeleteMapping(value = "/{chartId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.Chart,
            objectId = "{#p0}",
            msg = "{#executionSucceed? '删除数据图表成功' : '删除数据图表失败'}",
            activateData = "{#executionSucceed? #result.data : #p0}")
    public ApiResponse deleteDataChart(@PathVariable("chartId") String chartId) throws ApiException {
        return ApiResponse.success(this.chartDataConfigService.deleteChart(chartId));
    }
}
