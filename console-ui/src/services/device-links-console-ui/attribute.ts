// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增属性 **单个INT32类型属性：**
```json
{
    "productId": "101189666270609425",
    "moduleId": "156a891ac35b11ef91220242ac110004",
    "info": {
        "name": "测试int属性",
        "identifier": "testInt",
        "dataType": "INT32",
        "addition": {
            "unitId": "",
            "valueRange": {
                "min": 100,
                "max": 2000
            }
        },
        "description": "测试int属性"
    }
}
```

**数组中单个元素STRING类型：**
```json
{
    "productId": "101189666270609425",
    "moduleId": "156a891ac35b11ef91220242ac110004",
    "info": {
        "name": "测试array属性",
        "identifier": "testArray",
        "dataType": "ARRAY",
        "addition": {
            "elementCount": 10,
            "elementDataType": "STRING"
        },
        "description": "测试Array属性"
    }
}
```

**数组中单个元素OBJECT类型，每个元素配置两个子属性：**
```json
{
    "productId": "101189666270609425",
    "moduleId": "156a891ac35b11ef91220242ac110004",
    "info": {
        "name": "测试array属性",
        "identifier": "testArray",
        "dataType": "ARRAY",
        "addition": {
            "elementCount": 10,
            "elementDataType": "OBJECT"
        },
        "description": "测试Array属性"
    },
    "childAttributes": [
        {
            "name": "数组元素string属性",
            "identifier": "elementString",
            "dataType": "STRING",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "dataLength": 10240
            },
            "description": "数组中单个元素的属性"
        },
        {
            "name": "数组元素int属性",
            "identifier": "elementInt",
            "dataType": "INT32",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "valueRange": {
                    "min": 10,
                    "max": 1000
                }
            },
            "description": "数组中单个元素的属性"
        }
    ]
}
```

**OBJECT对象属性以及子属性：**
```json
{
    "productId": "101189666270609425",
    "moduleId": "156a891ac35b11ef91220242ac110004",
    "info": {
        "name": "测试对象属性",
        "identifier": "testObject",
        "dataType": "OBJECT",
        "description": "测试对象属性"
    },
    "childAttributes": [
        {
            "name": "测试String属性",
            "identifier": "subString",
            "dataType": "STRING",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "dataLength": 10240
            },
            "description": "测试子属性"
        },
        {
            "name": "测试int属性",
            "identifier": "subInt",
            "dataType": "INT32",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "valueRange": {
                    "min": 10,
                    "max": 1000
                }
            },
            "description": "测试子属性"
        },
        {
            "name": "测试long属性",
            "identifier": "subLong",
            "dataType": "LONG64",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "valueRange": {
                    "min": 10,
                    "max": 1000
                }
            },
            "description": "测试子属性"
        },
        {
            "name": "测试float属性",
            "identifier": "subFloat",
            "dataType": "FLOAT",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "valueRange": {
                    "min": 10.0,
                    "max": 1000.0
                }
            },
            "description": "测试子属性"
        },
        {
            "name": "测试double属性",
            "identifier": "subDouble",
            "dataType": "DOUBLE",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "valueRange": {
                    "min": 10.00,
                    "max": 1000.00
                }
            },
            "description": "测试子属性"
        },
        {
            "name": "测试enum属性",
            "identifier": "subEnum",
            "dataType": "ENUM",
            "addition": {
                "unitId": "516363ecd4ab11efa7700242c0a8d704",
                "valueMap": {
                    "enum1": "枚举1",
                    "enum2": "枚举2",
                    "enum3": "枚举3"
                }
            },
            "description": "测试子属性"
        },
        {
            "name": "测试boolean属性",
            "identifier": "subBoolean",
            "dataType": "BOOLEAN",
            "addition": {
                "unitId": "",
                "valueMap": {
                    "0": "关闭",
                    "1": "打开"
                }
            },
            "description": "测试子属性"
        },
        {
            "name": "测试Date属性",
            "identifier": "subDate",
            "dataType": "DATE",
            "addition": {
                "unitId": ""
            },
            "description": "测试子属性"
        },
        {
            "name": "测试DateTime属性",
            "identifier": "subDateTime",
            "dataType": "DATETIME",
            "addition": {
                "unitId": ""
            },
            "description": "测试子属性"
        },
        {
            "name": "测试Time属性",
            "identifier": "subTime",
            "dataType": "TIME",
            "addition": {
                "unitId": ""
            },
            "description": "测试子属性"
        },
        {
            "name": "测试Timestamp属性",
            "identifier": "subTimestamp",
            "dataType": "TIMESTAMP",
            "addition": {
                "unitId": ""
            },
            "description": "测试子属性"
        }
    ]
}
```
 POST /api/attribute */
export async function postApiAttribute(
  body: API.AddAttributeRequest,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponse>('/api/attribute', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询属性详情 GET /api/attribute/${param0} */
export async function getApiAttributeAttributeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiAttributeAttributeIdParams,
  options?: { [key: string]: any },
) {
  const { attributeId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Attribute | null;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/attribute/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新属性 更新属性Body的请求json数据格式与新增属性一致，可参考新增数据的json数据格式 POST /api/attribute/${param0} */
export async function postApiAttributeAttributeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiAttributeAttributeIdParams,
  body: API.UpdateAttributeRequest,
  options?: { [key: string]: any },
) {
  const { attributeId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Attribute | null;
    additional: Record<string, any>;
    success?: boolean;
  }>(`/api/attribute/${param0}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除属性 DELETE /api/attribute/${param0} */
export async function deleteApiAttributeAttributeId(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiAttributeAttributeIdParams,
  options?: { [key: string]: any },
) {
  const { attributeId: param0, ...queryParams } = params;
  return request<API.ApiResponse>(`/api/attribute/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询属性列表 POST /api/attribute/filter */
export async function postApiAttributeFilter(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiAttributeFilterParams,
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
      result: API.Attribute[];
    };
    additional: Record<string, any>;
    success?: boolean;
  }>('/api/attribute/filter', {
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

/** 查询设备功能下全部属性最新值 GET /api/device/${param0}/module/${param1}/attribute/latest */
export async function getApiDeviceDeviceIdModuleModuleIdAttributeLatest(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiDeviceDeviceIdModuleModuleIdAttributeLatestParams,
  options?: { [key: string]: any },
) {
  const { deviceId: param0, moduleId: param1, ...queryParams } = params;
  return request<API.ApiResponse>(`/api/device/${param0}/module/${param1}/attribute/latest`, {
    method: 'GET',
    params: {
      ...queryParams,
    },
    ...(options || {}),
  });
}

/** 删除期望属性 DELETE /api/device/attribute/${param0}/desired */
export async function deleteApiDeviceAttributeDesiredAttributeIdDesired(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteApiDeviceAttributeDesiredAttributeIdDesiredParams,
  options?: { [key: string]: any },
) {
  const { desiredAttributeId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.DeviceAttributeDesired;
    additional: Record<string, any>;
  }>(`/api/device/attribute/${param0}/desired`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 提取未知期望属性 POST /api/device/attribute/${param0}/desired/extract */
export async function postApiDeviceAttributeDesiredAttributeIdDesiredExtract(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceAttributeDesiredAttributeIdDesiredExtractParams,
  body: API.ExtractUnknownDesiredAttributeRequest,
  options?: { [key: string]: any },
) {
  const { desiredAttributeId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Attribute;
    additional: Record<string, any>;
  }>(`/api/device/attribute/${param0}/desired/extract`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 提取未知设备属性 POST /api/device/attribute/${param0}/extract */
export async function postApiDeviceAttributeDeviceAttributeIdExtract(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.postApiDeviceAttributeDeviceAttributeIdExtractParams,
  body: API.ExtractUnknownDeviceAttributeRequest,
  options?: { [key: string]: any },
) {
  const { deviceAttributeId: param0, ...queryParams } = params;
  return request<{
    code: string;
    message: string;
    data: API.Attribute;
    additional: Record<string, any>;
  }>(`/api/device/attribute/${param0}/extract`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}
