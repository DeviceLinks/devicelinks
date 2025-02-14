// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增用户 */
export async function addUserApi(options?: { [key: string]: any }) {
  return request<{ code: string; message: string; data: any; additional: Record<string, any> }>(
    '/api/user',
    {
      method: 'POST',
      ...(options || {}),
    },
  );
}

/** 查询用户 */
export async function pageUserApi(params: any, body: any, options?: { [key: string]: any }) {
  console.log(params, body);
  return request<{ code: string; message: string; data: any; additional: Record<string, any> }>(
    '/api/user',
    {
      method: 'GET',
      params,
      data: body,
      ...(options || {}),
    },
  );
}
