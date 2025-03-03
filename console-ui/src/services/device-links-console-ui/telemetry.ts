// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 获取遥测数据列表 POST /api/telemetry/filter */
export async function postApiTelemetryFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiTelemetryFilterParams,
  body: API.SearchField,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.Telemetry;
    additional: Record<string, any>;
  }>('/api/telemetry/filter', {
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
