// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 新增用户 */
export async function addUserApi(
  body: {
    /**
     * 账号
     */
    account: string;
    /**
     * 激活方式
     */
    activateMethod: string;
    /**
     * 所属部门ID
     */
    departmentId?: string;
    /**
     * 邮箱地址
     */
    email?: string;
    /**
     * 备注
     */
    mark?: string;
    /**
     * 手机号
     */
    phone?: string;
    /**
     * 用户名
     */
    username: string;
  },
  options?: { [key: string]: any },
) {
  return request<{ code: string; message: string; data: any; additional: Record<string, any> }>(
    '/api/user',
    {
      method: 'POST',
      data: body,
      ...(options || {}),
    },
  );
}

/** 查询用户 */
export async function pageUserApi(
  params: {
    /**
     * 当前页码
     */
    page?: string;
    /**
     * 每页条数
     */
    pageSize?: string;
    /**
     * 排序顺序，ASC：正序；DESC：倒序
     */
    sortDirection?: string;
    /**
     * 排序属性，接口返回值"result"中的属性都可以作为排序字段
     */
    sortProperty?: string;
  },
  body: {
    /**
     * 检索字段模块，User/Device/Log/Notification
     */
    searchFieldModule: string;
    /**
     * 检索字段列表
     */
    searchFields?: {
      /**
       * 检索字段
       */
      field: string;
      /**
       * 检索运算符，详见检索字段列表接口返回值
       */
      operator: string;
      /**
       * 匹配检索值列表
       */
      value: Array<number | string>;
      [property: string]: any;
    };
    /**
     * 检索字段之间的匹配方式，ALL：所有；ANY：任意
     */
    searchMatch: string;
  },
  options?: { [key: string]: any },
) {
  return request<{ code: string; message: string; data: any; additional: Record<string, any> }>(
    '/api/user',
    {
      method: 'GET',
      params,
      data: body,
      ...(options || {}),
    },
  );
}
