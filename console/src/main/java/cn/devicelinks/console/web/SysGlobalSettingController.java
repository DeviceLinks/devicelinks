package cn.devicelinks.console.web;

import cn.devicelinks.console.model.setting.UpdateGlobalSettingRequest;
import cn.devicelinks.console.service.SysGlobalSettingService;
import cn.devicelinks.framework.common.LogAction;
import cn.devicelinks.framework.common.LogObjectType;
import cn.devicelinks.framework.common.api.ApiResponse;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.operate.log.OperationLog;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 全局参数设置接口控制器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/global/setting")
@AllArgsConstructor
public class SysGlobalSettingController {

    private SysGlobalSettingService globalSettingService;

    /**
     * 获取全部全局参数设置
     *
     * @return 全局参数设置列表
     * @throws ApiException 如果在处理请求时发生错误，例如查询失败。
     */
    @GetMapping
    public ApiResponse getGlobalSettings() throws ApiException {
        return ApiResponse.success(this.globalSettingService.selectEnabledList());
    }

    /**
     * 更新全局参数设置
     *
     * @param request 包含新全局配置参数的请求实体，需通过有效性校验。具体字段定义参见{@link UpdateGlobalSettingRequest}
     * @return 标准化接口响应实体{@link ApiResponse}，包含操作状态码和消息。成功时无额外数据返回
     * @throws ApiException 当出现以下情况时抛出：
     *                      1. 请求体参数校验不通过（如格式错误/缺失必填字段）
     *                      2. 配置更新过程中发生不可预期的系统异常
     */
    @PostMapping
    @OperationLog(action = LogAction.Update,
            objectId = "{#p0.settingId}",
            objectType = LogObjectType.GlobalSetting,
            msg = "{#executionSucceed? '全局参数设置更新成功' : '全局参数设置更新失败'}",
            activateData = "{#p0}")
    public ApiResponse updateGlobalSetting(@Valid @RequestBody UpdateGlobalSettingRequest request) throws ApiException {
        return ApiResponse.success(this.globalSettingService.updateGlobalSettingValue(request.getSettingId(), request.getValue()));
    }
}
