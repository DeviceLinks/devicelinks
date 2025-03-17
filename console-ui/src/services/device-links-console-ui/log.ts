// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 查询日志列表 POST /api/log/filter */
export async function postApiLogFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiLogFilterParams,
  body: {
    /** 检索字段模块 */
    searchFieldModule: string;
    /** 检索字段之间的匹配方式 */
    searchMatch: 'ANY' | 'ALL';
    /** 检索字段列表 */
    searchFields?: API.SearchFieldItem[];
  },
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: {
      page: number;
      pageSize: number;
      totalPages: number;
      totalRows: number;
      result: API.Log[];
    };
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
