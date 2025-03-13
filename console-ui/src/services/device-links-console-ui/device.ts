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
    data: API.DeviceDetailInfo;
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
  body: {
    /** 属性知晓类型 */
    knowType: string;
    /** 属性ID，对于已经在功能模块下定义的属性需要传递属性ID，已知属性类型必填 */
    attributeId?: string;
    /** 属性标识符，未知属性必填 */
    identifier?: string;
    /** 数据类型，未知属性必填 */
    dataType?: string;
    /** 属性期望值，根据数据类型传递，ARRAY类型直接传递"[xx,xxx,xx]"即可 */
    desiredValue: string;
  },
  options?: { [key: string]: any },
) {
  const { deviceId: param0, moduleId: param1, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DesiredAttribute;
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
  body: {
    /** 数据类型，未知属性时传递该参数 */
    dataType?: string;
    /** 期望值 */
    desiredValue: string;
  },
  options?: { [key: string]: any },
) {
  const { desiredAttributeId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DesiredAttribute;
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
  body: API.SearchField,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.DesiredAttribute;
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
  body: API.SearchField,
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.DeviceAttribute;
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
