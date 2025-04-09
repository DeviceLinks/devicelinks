// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增设备标签 POST /api/device-tag */
export async function postApiDeviceTag(
  body: API.AddDeviceTagRequest,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.DeviceTag;
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/device-tag', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除设备标签 DELETE /api/device-tag/${param0} */
export async function deleteApiDeviceTagTagId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiDeviceTagTagIdParams,
  options?: { [key: string]: any },
) {
  const { tagId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceTag;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/device-tag/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询设备标签列表 POST /api/device-tag/filter */
export async function postApiDeviceTagFilter(
  body: API.SearchFieldQuery,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.DeviceTag;
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/device-tag/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
