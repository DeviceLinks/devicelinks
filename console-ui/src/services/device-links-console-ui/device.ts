// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增设备 POST /api/device */
export async function postApiDevice(body: API.AddDeviceRequest, options?: { [key: string]: any }) {
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

/** 查询设备信息 GET /api/device/${param0} */
export async function getApiDeviceDeviceId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceDeviceIdParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceDTO;
    additional: Record<string, any>;
  }>(`/api/device/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新设备 POST /api/device/${param0} */
export async function postApiDeviceDeviceId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdParams,
  body: API.UpdateDeviceRequest,
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

/** 查询设备凭证 GET /api/device/${param0}/credentials */
export async function getApiDeviceDeviceIdCredentials(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceDeviceIdCredentialsParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceCredentials;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/credentials`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新设备凭证 POST /api/device/${param0}/credentials */
export async function postApiDeviceDeviceIdCredentials(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdCredentialsParams,
  body: API.UpdateDeviceCredentialsRequest,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceCredentials;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/credentials`, {
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
  return request<API.ApiResponse>(`/api/device/${param0}/enabled`, {
    method: 'POST',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 查询设备功能模块列表 GET /api/device/${param0}/function/module */
export async function getApiDeviceDeviceIdOpenApiFunctionModule(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceDeviceId_openAPI_functionModuleParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.FunctionModule;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/function/module`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 新增设备期望属性 新增期望值时，未知属性除了ENUM数据类型不允许使用，其他数据类型都可以。

同一个功能模块下相同标识符的属性，如果已经存在则执行更新，version+1。 POST /api/device/${param0}/module/${param1}/attribute/desired */
export async function postApiDeviceDeviceIdModuleModuleIdAttributeDesired(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdModuleModuleIdAttributeDesiredParams,
  body: API.AddDeviceDesiredAttributeRequest,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, moduleId: param1, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceAttributeDesired;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/module/${param1}/attribute/desired`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 查询设备密钥 GET /api/device/${param0}/secret */
export async function getApiDeviceDeviceIdSecret(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceDeviceIdSecretParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceSecretDTO;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device/${param0}/secret`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 重新生成设备密钥 POST /api/device/${param0}/secret-regenerate */
export async function postApiDeviceDeviceIdSecretRegenerate(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceDeviceIdSecretRegenerateParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceSecretDTO;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device/${param0}/secret-regenerate`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询设备影子 GET /api/device/${param0}/shadow */
export async function getApiDeviceDeviceIdShadow(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceDeviceIdShadowParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceShadow;
    additional: Record<string, any>;
  }>(`/api/device/${param0}/shadow`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新设备期望属性 POST /api/device/attribute/${param0}/desired */
export async function postApiDeviceAttributeDesiredAttributeIdDesired(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceAttributeDesiredAttributeIdDesiredParams,
  body: API.UpdateDeviceDesiredAttributeRequest,
  options?: { [key: string]: any },
) {
  const { desiredAttributeId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceAttributeDesired;
    additional: Record<string, any>;
  }>(`/api/device/attribute/${param0}/desired`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 查询设备期望属性 POST /api/device/attribute/desired/filter */
export async function postApiDeviceAttributeDesiredFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceAttributeDesiredFilterParams,
  body: {},
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.DeviceAttributeDesired;
    additional: Record<string, any>;
  }>('/api/device/attribute/desired/filter', {
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

/** 查询设备属性列表 POST /api/device/attribute/filter */
export async function postApiDeviceAttributeFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceAttributeFilterParams,
  body: API.SearchFieldQuery,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.DeviceAttributeDTO;
    additional: Record<string, any>;
  }>('/api/device/attribute/filter', {
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

/** 查询设备列表 POST /api/device/filter */
export async function postApiDeviceFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceFilterParams,
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
      result: API.Device[];
    };
    additional: Record<string, any>;
    success?: boolean;
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
