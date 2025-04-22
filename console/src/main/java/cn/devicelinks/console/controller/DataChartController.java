package cn.devicelinks.console.controller;

import cn.devicelinks.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.api.support.authorization.UserAuthorizedAddition;
import cn.devicelinks.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.service.device.DataChartService;
import cn.devicelinks.component.web.search.SearchFieldQuery;
import cn.devicelinks.api.model.request.AddDataChartRequest;
import cn.devicelinks.component.web.search.annotation.SearchModule;
import cn.devicelinks.common.LogAction;
import cn.devicelinks.common.LogObjectType;
import cn.devicelinks.component.web.api.ApiResponse;
import cn.devicelinks.component.web.api.ApiException;
import cn.devicelinks.component.operate.log.annotation.OperationLog;
import cn.devicelinks.entity.DataChart;
import cn.devicelinks.component.web.search.SearchFieldModuleIdentifier;
import cn.devicelinks.api.model.dto.DataChartDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private DataChartService dataChartService;

    /**
     * 分页查询数据图表
     *
     * @param searchFieldQuery 检索字段参数实体 {@link SearchFieldQuery}
     * @return 数据图表列表 {@link DataChartDTO}
     * @throws ApiException 查询过程中遇到的业务逻辑异常
     */
    @PostMapping(value = "/filter")
    @SearchModule(module = SearchFieldModuleIdentifier.DataChart)
    public ApiResponse<List<DataChartDTO>> getDataChartByPageable(@Valid @RequestBody SearchFieldQuery searchFieldQuery) throws ApiException {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        return ApiResponse.success(this.dataChartService.getDataChartList(searchFieldConditionList));
    }

    /**
     * 新增数据图表
     *
     * @param request 新增数据图表请求参数实体 {@link AddDataChartRequest}
     * @return 已添加的数据图表ID {@link DataChart#getId()}
     * @throws ApiException 新增过程中遇到的业务逻辑异常
     */
    @PostMapping
    @OperationLog(action = LogAction.Add,
            objectType = LogObjectType.Chart,
            objectId = "{#executionSucceed? #result.data.id : #p0.targetId}",
            msg = "{#executionSucceed? '新增数据图表成功' : '新增数据图表失败'}",
            activateData = "{#p0}")
    public ApiResponse<DataChartDTO> addDataChart(@Valid @RequestBody AddDataChartRequest request) throws ApiException {
        UserAuthorizedAddition authorizedAddition = UserDetailsContext.getUserAddition();
        return ApiResponse.success(this.dataChartService.addChart(request, authorizedAddition));
    }

    /**
     * 删除数据图表
     *
     * @param chartId 数据图表ID {@link DataChart#getId()}
     * @return 已删除的数据图表 {@link DataChart}
     * @throws ApiException 删除过程中遇到的业务逻辑异常
     */
    @DeleteMapping(value = "/{chartId}")
    @OperationLog(action = LogAction.Delete,
            objectType = LogObjectType.Chart,
            objectId = "{#p0}",
            msg = "{#executionSucceed? '删除数据图表成功' : '删除数据图表失败'}",
            activateData = "{#executionSucceed? #result.data : #p0}")
    public ApiResponse<DataChart> deleteDataChart(@PathVariable("chartId") String chartId) throws ApiException {
        return ApiResponse.success(this.dataChartService.deleteChart(chartId));
    }
}
