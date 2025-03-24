// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增用户 POST /api/user */
export async function postApiUser(body: API.AddUserRequest, options?: { [key: string]: any }) {
  return request<{
    code: string;
    message: string;
    data: API.User | null;
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/user', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取用户详情 GET /api/user/${param0} */
export async function getApiUserUserId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiUserUserIdParams,
  options?: { [key: string]: any },
) {
  const { userId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.User;
    additional: Record<string, any>;
  }>(`/api/user/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新用户 POST /api/user/${param0} */
export async function postApiUserUserId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiUserUserIdParams,
  body: API.UpdateUserRequest,
  options?: { [key: string]: any },
) {
  const { userId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.User | null;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/user/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除用户 DELETE /api/user/${param0} */
export async function deleteApiUserUserId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiUserUserIdParams,
  options?: { [key: string]: any },
) {
  const { userId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: null;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/user/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询用户列表 POST /api/user/filter */
export async function postApiUserFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiUserFilterParams,
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
      result: API.User[];
    };
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/user/filter', {
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

/** 获取登录人信息 GET /api/user/me */
export async function getApiUserMe(options?: { [key: string]: any }) {
  return request<{
    code: string;
    message: string;
    data: API.CurrentLoginUser | null;
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/user/me', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 启用 / 禁用 POST /api/user/status/${param0} */
export async function postApiUserStatusUserId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiUserStatusUserIdParams,
  options?: { [key: string]: any },
) {
  const { userId: param0, ...queryParams } = params;
  return request<API.ApiResponse>(`/api/user/status/${param0}`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}
