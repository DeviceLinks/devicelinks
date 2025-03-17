// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增数据图表（设备属性） POST /api/chart */
export async function postApiChart(
  body: {
    chartName: string;
    chartType: string;
    targetId: string;
    targetLocation: string;
    mark?: string;
    fields: { fieldId: string; fieldType: string; fieldLabel: string }[];
  },
  options?: { [key: string]: any },
) {
  return request<{
    code: string;
    message: string;
    data: API.Chart;
    additional: Record<string, any>;
  }>('/api/chart', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询数据图表列表 POST /api/chart/filter */
export async function postApiChartFilter(body: API.SearchField, options?: { [key: string]: any }) {
  return request<{
    code: string;
    message: string;
    data: API.Chart;
    additional: Record<string, any>;
  }>('/api/chart/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
