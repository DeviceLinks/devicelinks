// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增产品 POST /api/product */
export async function postApiProduct(
  body: API.AddProductRequest,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.Product | null;
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/product', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询产品详情 GET /api/product/${param0} */
export async function getApiProductProductId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiProductProductIdParams,
  options?: { [key: string]: any },
) {
  const { productId: param0, ...queryParams } = params;
  return request<{
    code: boolean;
    message: string;
    data: API.Product;
    additional: Record<string, any>;
  }>(`/api/product/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新产品 POST /api/product/${param0} */
export async function postApiProductProductId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiProductProductIdParams,
  body: API.UpdateProductRequest,
  options?: { [key: string]: any },
) {
  const { productId: param0, ...queryParams } = params;
  return request<{
    code: boolean;
    message: string;
    data: API.Product | null;
    additional: Record<string, any>;
  }>(`/api/product/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除产品 DELETE /api/product/${param0} */
export async function deleteApiProductProductId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiProductProductIdParams,
  options?: { [key: string]: any },
) {
  const { productId: param0, ...queryParams } = params;
  return request<{
    code: boolean;
    message: string;
    data: API.Product | null;
    additional: Record<string, any>;
  }>(`/api/product/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 发布产品 POST /api/product/${param0}/publish */
export async function postApiProductProductIdPublish(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiProductProductIdPublishParams,
  options?: { [key: string]: any },
) {
  const { productId: param0, ...queryParams } = params;
  return request<{
    code: boolean;
    message: string;
    data: API.Product;
    additional: Record<string, any>;
  }>(`/api/product/${param0}/publish`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 重新生成KeySecret POST /api/product/${param0}/regenerate/key-secret */
export async function postApiProductProductIdRegenerateKeySecret(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiProductProductIdRegenerateKeySecretParams,
  options?: { [key: string]: any },
) {
  const { productId: param0, ...queryParams } = params;
  return request<{
    code: boolean;
    message: string;
    data: API.RegenerateKeySecretResponse;
    additional: Record<string, any>;
  }>(`/api/product/${param0}/regenerate/key-secret`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询产品列表 POST /api/product/filter */
export async function postApiProductFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiProductFilterParams,
  body: API.SearchFieldQuery,
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
      result: API.Product[];
    };
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/product/filter', {
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
