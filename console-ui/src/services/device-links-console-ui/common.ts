// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 查询枚举列表 返回前端可用的全部枚举定义，包含枚举值、描述。 GET /api/common/enums */
export async function getApiCommonEnums(options?: { [key: string]: any }) {
  return request<{
    code: string;
    message: string;
    data: {
      AttributeDataType?: { label?: string; value?: string }[];
      OtaUpgradeStrategyRetryInterval?: { label?: string; value?: string }[];
      SearchFieldComponentType?: { label?: string; value?: string }[];
      SessionStatus?: { label?: string; value?: string }[];
      NotificationPushAway?: { label?: string; value?: string }[];
      NotificationType?: { label?: string; value?: string }[];
      AlarmType?: { label?: string; value?: string }[];
      SearchFieldOperator?: { label?: string; value?: string }[];
      OtaUpgradeBatchMethod?: { label?: string; value?: string }[];
      UserActivateMethod?: { label?: string; value?: string }[];
      DeviceType?: { label?: string; value?: string }[];
      OtaUpgradeBatchScope?: { label?: string; value?: string }[];
      NotificationSeverity?: { label?: string; value?: string }[];
      ProductStatus?: { label?: string; value?: string }[];
      DeviceNetworkingAway?: { label?: string; value?: string }[];
      SearchFieldValueType?: { label?: string; value?: string }[];
      PlatformType?: { label?: string; value?: string }[];
      AccessGatewayProtocol?: { label?: string; value?: string }[];
      DeviceStatus?: { label?: string; value?: string }[];
      OtaUpgradeStrategyType?: { label?: string; value?: string }[];
      SearchFieldOptionDataSource?: { label?: string; value?: string }[];
      SearchFieldModuleIdentifier?: { label?: string; value?: string }[];
      OtaUpgradeBatchType?: { label?: string; value?: string }[];
      GlobalSettingDataType?: { label?: string; value?: string }[];
      SignatureAlgorithm?: { label?: string; value?: string }[];
      NotificationMatchUserAway?: { label?: string; value?: string }[];
      LogAction?: { label?: string; value?: string }[];
      DeviceAuthenticationMethod?: { label?: string; value?: string }[];
      NotificationTypeIdentifier?: { label?: string; value?: string }[];
      SignAlgorithm?: { label?: string; value?: string }[];
      DataFormat?: { label?: string; value?: string }[];
      OtaPackageType?: { label?: string; value?: string }[];
      OtaUpgradeProgressState?: { label?: string; value?: string }[];
      OtaPackageDownloadProtocol?: { label?: string; value?: string }[];
      EntityAction?: { label?: string; value?: string }[];
      NotificationStatus?: { label?: string; value?: string }[];
      SearchFieldMatch?: { label?: string; value?: string }[];
      OTAFileSource?: { label?: string; value?: string }[];
      LogObjectType?: { label?: string; value?: string }[];
      OtaUpgradeBatchState?: { label?: string; value?: string }[];
      UserIdentity?: { label?: string; value?: string }[];
    };
    additional: Record<string, any>;
  }>('/api/common/enums', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 查询检索字段 GET /api/common/search/field */
export async function getApiCommonSearchField(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiCommonSearchFieldParams,
  options?: { [key: string]: any },
) {
  return request<{
    field: string;
    fieldText: string;
    valueType: API.SearchFieldValueType;
    componentType: string;
    optionDataSource: string;
    optionApiDataCode: any;
    optionStaticData: { label?: string; value?: string }[];
    operators: { description?: string; value?: string }[];
  }>('/api/common/search/field', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新全局设置 POST /api/global/setting */
export async function postApiGlobalSetting(
  body: {
    /** 设置ID */
    settingId: string;
    /** 设置值 */
    value: string;
  },
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.GlobalParam;
    additional: Record<string, any>;
  }>('/api/global/setting', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询全局设置列表 GET /api/setting */
export async function getApiSetting(options?: { [key: string]: any }) {
  return request<{
    code: string;
    message: string;
    data: API.GlobalParam;
    additional: Record<string, any>;
  }>('/api/setting', {
    method: 'GET',
    ...(options || {}),
  });
}
