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
    code: string;
    message: string;
    data: {
      page?: number;
      pageSize?: number;
      totalPages?: number;
      totalRows?: number;
      result?: API.Product[];
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

/** 新增产品 POST /api/product */
export async function postApiProduct(
  body: {
    /** 名称 */
    name: string;
    /** 设备类型 */
    deviceType: string;
    /** 设备网络方式 */
    networkingAway: string;
    /** 接入网关协议，仅网关子设备类型传递该参数 */
    accessGatewayProtocol: string;
    /** 数据格式 */
    dataFormat: string;
    /** 鉴权方式 */
    authenticationMethod: string;
    /** 描述 */
    description: string;
  },
  options?: { [key: string]: any },
) {
  return request<API.ResponseResult>('/api/product', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
