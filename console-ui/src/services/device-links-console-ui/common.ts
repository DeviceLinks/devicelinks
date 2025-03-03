// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 查询检索字段 GET /api/common/search/field */
export async function getApiCommonSearchField(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiCommonSearchFieldParams,
  options?: { [key: string]: any },
) {
  return request<{
    field: string;
    fieldText: string;
    valueType: API.SearchFieldValueType;
    componentType: string;
    optionDataSource: string;
    optionApiDataCode: any;
    optionStaticData: { label?: string; value?: string }[];
    operators: { description?: string; value?: string }[];
  }>('/api/common/search/field', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
