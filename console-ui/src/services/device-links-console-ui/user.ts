// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增用户 POST /api/user */
export async function postApiUser(
  body: {
    /** 用户名 */
    username: string;
    /** 账号 */
    account: string;
    /** 邮箱地址 */
    email?: string;
    /** 手机号 */
    phone?: string;
    /** 所属部门ID */
    departmentId?: string;
    /** 激活方式 */
    activateMethod: string;
    /** 备注 */
    mark?: string;
  },
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.User | null;
    additional: Record<string, any>;
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
  body: {
    /** 用户名 */
    username: string;
    /** 邮箱地址 */
    email: string;
    /** 手机号 */
    phone: string;
    /** 所属部门ID */
    departmentId: string;
    /** 备注 */
    mark: string;
  },
  options?: { [key: string]: any },
) {
  const { userId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.User | null;
    additional: Record<string, any>;
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
  return request<{ code: string; message: string; data: null; additional: Record<string, any> }>(
    `/api/user/${param0}`,
    {
      method: 'DELETE',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 查询用户列表 POST /api/user/filter */
export async function postApiUserFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiUserFilterParams,
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
      result: API.User[];
    };
    additional: Record<string, any>;
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
    data: API.CurrentUser | null;
    additional: Record<string, any>;
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
  return request<API.ResponseResult>(`/api/user/status/${param0}`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}
