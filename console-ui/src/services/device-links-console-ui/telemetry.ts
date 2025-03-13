// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增设备遥测数据 POST /api/device/${param0}/telemetry */
export async function postApiDeviceDeviceIdTelemetry(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdTelemetryParams,
  body: {
    /** 遥测数据标识符 */
    identifier: string;
    /** 遥测数据值 */
    value: string;
    /** 数据类型 */
    dataType: string;
  },
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Telemetry;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/telemetry`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除设备遥测数据 DELETE /api/device/${param0}/telemetry/${param1} */
export async function deleteApiDeviceDeviceIdTelemetryTelemetryId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiDeviceDeviceIdTelemetryTelemetryIdParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, telemetryId: param1, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Telemetry;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/telemetry/${param1}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取设备遥测数据列表 POST /api/device/telemetry/filter */
export async function postApiDeviceTelemetryFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceTelemetryFilterParams,
  body: API.SearchField,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.Telemetry;
    additional: Record<string, any>;
  }>('/api/device/telemetry/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}
