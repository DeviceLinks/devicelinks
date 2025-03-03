// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增设备 POST /api/device */
export async function postApiDevice(
  body: {
    /** 产品ID */
    productId: string;
    /** 部门ID */
    departmentId: string;
    /** 设备类型 */
    deviceType: string;
    /** 设备唯一码 */
    deviceCode: string;
    /** 备注名称 */
    name: string;
    /** 标签列表 */
    tags: string[];
    /** 备注 */
    mark: string;
    /** 鉴权方式 */
    authenticationMethod: string;
    /** 鉴权附加信息 */
    authenticationAddition: {
      accessToken?: string;
      x509Pem?: string;
      mqttBasic: { clientId?: string; username?: string; password?: string };
      deviceCredential: { deviceKey?: string; deviceSecret?: string };
    };
  },
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.Device;
    additional: Record<string, any>;
  }>('/api/device', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新设备 POST /api/device/${param0} */
export async function postApiDeviceDeviceId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdParams,
  body: {
    /** 设备备注名称 */
    name: string;
    /** 设备标签列表 */
    tags: string[];
    /** 备注信息 */
    mark: string;
  },
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Device;
    additional: Record<string, any>;
  }>(`/api/device/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除设备 DELETE /api/device/${param0} */
export async function deleteApiDeviceDeviceId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiDeviceDeviceIdParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Device;
    additional: Record<string, any>;
  }>(`/api/device/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询设备凭证 GET /api/device/${param0}/authorization */
export async function getApiDeviceDeviceIdAuthorization(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceDeviceIdAuthorizationParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceAuth;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/authorization`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新设备凭证 POST /api/device/${param0}/authorization */
export async function postApiDeviceDeviceIdAuthorization(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdAuthorizationParams,
  body: {
    /** 认证方式 */
    authenticationMethod: string;
    /** 认证附加信息 */
    authenticationAddition: {
      accessToken?: string;
      x509Pem?: string;
      mqttBasic: { clientId?: string; username?: string; password?: string };
      deviceCredential: { deviceKey?: string; deviceSecret?: string };
    };
  },
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<API.ResponseResult>(`/api/device/${param0}/authorization`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 启用/禁用设备 POST /api/device/${param0}/enabled */
export async function postApiDeviceDeviceIdEnabled(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdEnabledParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<API.ResponseResult>(`/api/device/${param0}/enabled`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 查询设备列表 POST /api/device/filter */
export async function postApiDeviceFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceFilterParams,
  body: {
    /** 检索字段模块 */
    searchFieldModule: string;
    /** 检索字段之间的匹配方式 */
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
      result?: API.Device[];
    };
    additional: Record<string, any>;
  }>('/api/device/filter', {
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
