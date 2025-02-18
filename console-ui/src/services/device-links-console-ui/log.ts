// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 查询日志列表 POST /api/log/filter */
export async function postApiLogFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiLogFilterParams,
  body: {
    /** 检索字段模块，User/Device/Log/Notification */
    searchFieldModule: string;
    /** 检索字段之间的匹配方式，ALL：所有；ANY：任意 */
    searchMatch: string;
    /** 检索字段列表 */
    searchFields?: { field: string; operator: string; value: (string | number)[] }[];
  },
  options?: { [key: string]: any },
) {
  return request<{
    code: boolean;
    message: string;
    data: API.E697A5E5BF97E6A8A1E59E8B;
    additional: Record<string, any>;
  }>('/api/log/filter', {
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
