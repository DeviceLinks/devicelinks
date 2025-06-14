// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 登录 POST /auth/login */
export async function postAuthLogin(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postAuthLoginParams,
  options?: { [key: string]: any },
) {
  return request<API.LoginResult>('/auth/login', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 退出登录 POST /auth/logout */
export async function postAuthLogout(options?: { [key: string]: any }) {
  return request<API.ApiResponse>('/auth/logout', {
    method: 'POST',
    ...(options || {}),
  });
}
