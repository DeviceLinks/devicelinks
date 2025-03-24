// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增部门 POST /api/department */
export async function postApiDepartment(
  body: API.AddDepartmentRequest,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponse>('/api/department', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取部门信息 GET /api/department/${param0} */
export async function getApiDepartmentDepartmentId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDepartmentDepartmentIdParams,
  options?: { [key: string]: any },
) {
  const { departmentId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Department;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/department/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新部门 POST /api/department/${param0} */
export async function postApiDepartmentDepartmentId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDepartmentDepartmentIdParams,
  body: API.UpdateDepartmentRequest,
  options?: { [key: string]: any },
) {
  const { departmentId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Department;
    additional: Record<string, any>;
  }>(`/api/department/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除部门 DELETE /api/department/${param0} */
export async function deleteApiDepartmentDepartmentId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiDepartmentDepartmentIdParams,
  options?: { [key: string]: any },
) {
  const { departmentId: param0, ...queryParams } = params;
  return request<API.ApiResponse>(`/api/department/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}
