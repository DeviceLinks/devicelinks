// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增设备配置文件 POST /api/device-profile */
export async function postApiDeviceProfile(
  body: API.AddDeviceProfileRequest,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.DeviceProfile;
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/device-profile', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询设备配置文件信息 GET /api/device-profile/${param0} */
export async function getApiDeviceProfileProfileId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceProfileProfileIdParams,
  options?: { [key: string]: any },
) {
  const { profileId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceProfile;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device-profile/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新设备配置文件 POST /api/device-profile/${param0} */
export async function postApiDeviceProfileProfileId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceProfileProfileIdParams,
  body: API.UpdateDeviceProfileRequest,
  options?: { [key: string]: any },
) {
  const { profileId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceProfile;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device-profile/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除设备配置文件 DELETE /api/device-profile/${param0} */
export async function deleteApiDeviceProfileProfileId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiDeviceProfileProfileIdParams,
  options?: { [key: string]: any },
) {
  const { profileId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceProfile;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device-profile/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新设备配置文件（基础信息） PUT /api/device-profile/${param0}/basic-info */
export async function putApiDeviceProfileProfileIdBasicInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.putApiDeviceProfileProfileIdBasicInfoParams,
  body: API.UpdateDeviceProfileBasicInfoRequest,
  options?: { [key: string]: any },
) {
  const { profileId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceProfile;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device-profile/${param0}/basic-info`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 更新设备配置文件（扩展配置） PUT /api/device-profile/${param0}/extension */
export async function putApiDeviceProfileProfileIdExtension(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.putApiDeviceProfileProfileIdExtensionParams,
  body: API.UpdateDeviceProfileExtensionRequest,
  options?: { [key: string]: any },
) {
  const { profileId: param0, ...queryParams } = params;
  return request<API.ApiResponse>(`/api/device-profile/${param0}/extension`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 更新设备配置文件（日志附加） PUT /api/device-profile/${param0}/log-addition */
export async function putApiDeviceProfileProfileIdLogAddition(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.putApiDeviceProfileProfileIdLogAdditionParams,
  body: API.UpdateDeviceProfileLogAdditionRequest,
  options?: { [key: string]: any },
) {
  const { profileId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceProfileLogAddition;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device-profile/${param0}/log-addition`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 查询设备配置文件列表 POST /api/device-profile/filter */
export async function postApiDeviceProfileFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceProfileFilterParams,
  body: API.SearchFieldQuery,
  options?: { [key: string]: any },
) {
  return request<{
    page: number;
    pageSize: number;
    totalPages: number;
    totalRows: number;
    result: API.DeviceProfile[];
  }>('/api/device-profile/filter', {
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
