// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 查询产品列表 GET /api/product */
export async function getApiProduct(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiProductParams,
  body: API.SearchField,
  options?: { [key: string]: any },
) {
  return request<{
    code: boolean;
    message: string;
    data: {
      page?: number;
      pageSize?: number;
      totalPages?: number;
      totalRows?: number;
      result?: API.Product;
    };
    additional: Record<string, any>;
  }>('/api/product', {
    method: 'GET',
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
