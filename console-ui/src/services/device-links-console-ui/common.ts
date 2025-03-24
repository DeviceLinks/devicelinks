// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 查询枚举列表 返回前端可用的全部枚举定义，包含枚举值、描述。 GET /api/common/enums */
export async function getApiCommonEnums(body: {}, options?: { [key: string]: any }) {
  return request<{
    code: string;
    message: string;
    data: API.Enum;
    additional: Record<string, any>;
  }>('/api/common/enums', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询检索字段 GET /api/common/search/field */
export async function getApiCommonSearchField(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiCommonSearchFieldParams,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.SearchField[];
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/common/search/field', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新全局设置 POST /api/global/setting */
export async function postApiGlobalSetting(
  body: {
    /** 设置ID */
    settingId: string;
    /** 设置值 */
    value: string;
  },
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.GlobalSetting;
    additional: Record<string, any>;
  }>('/api/global/setting', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询全局设置列表 GET /api/setting */
export async function getApiSetting(options?: { [key: string]: any }) {
  return request<{
    code: string;
    message: string;
    data: API.GlobalSetting;
    additional: Record<string, any>;
  }>('/api/setting', {
    method: 'GET',
    ...(options || {}),
  });
}
