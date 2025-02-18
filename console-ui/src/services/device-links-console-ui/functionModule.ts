// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 查询功能模块列表 GET /api/function/module */
export async function getApiOpenApiFunctionModule(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApi_openAPI_functionModuleParams,
  body: {
    /** 检索字段模块，User/Device/Log/Notification */
    searchFieldModule: string;
    /** 检索字段之间的匹配方式，ALL：所有；ANY：任意 */
    searchMatch: string;
    /** 检索字段列表 */
    searchFields?: { field?: string; operator?: string; value?: (string | number)[] }[];
  },
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
      result?: API.FunctionModule[];
    };
    additional: Record<string, any>;
  }>('/api/function/module', {
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

/** 添加功能模块 POST /api/function/module */
export async function postApiOpenApiFunctionModule(
  body: {
    productId: string;
    name: string;
    identifier: string;
  },
  options?: { [key: string]: any },
) {
  return request<API.ResponseResult>('/api/function/module', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询功能模块详情 GET /api/function/module/${param0} */
export async function getApiOpenApiFunctionModuleModuleId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApi_openAPI_functionModuleModuleIdParams,
  options?: { [key: string]: any },
) {
  const { moduleId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.FunctionModule;
    additional: Record<string, any>;
  }>(`/api/function/module/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新功能模块 POST /api/function/module/${param0} */
export async function postApiOpenApiFunctionModuleModuleId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApi_openAPI_functionModuleModuleIdParams,
  body: {
    name: string;
    identifier: string;
  },
  options?: { [key: string]: any },
) {
  const { moduleId: param0, ...queryParams } = params;
  return request<API.ResponseResult>(`/api/function/module/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除功能模块 DELETE /api/function/module/${param0} */
export async function deleteApiOpenApiFunctionModuleModuleId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApi_openAPI_functionModuleModuleIdParams,
  options?: { [key: string]: any },
) {
  const { moduleId: param0, ...queryParams } = params;
  return request<API.ResponseResult>(`/api/function/module/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}
