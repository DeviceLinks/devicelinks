/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import { RequestOptions } from '@@/plugin-request/request';
import { history, RequestConfig } from '@umijs/max';
import { message } from 'antd';
import Cookies from 'js-cookie';
const loginPath = '/user/login';
// 错误处理方案： 错误类型
enum ErrorShowType {
  SILENT = 0,
  WARN_MESSAGE = 1,
  ERROR_MESSAGE = 2,
  NOTIFICATION = 3,
  REDIRECT = 9,
}
enum ResponseCodeType {
  SUCCESS = 'SUCCESS',
  TOKEN_JWT_PARSING_FAILED = 'TOKEN_JWT_PARSING_FAILED',
}
// 与后端约定的响应数据格式
interface ResponseStructure {
  data: any;
  code?: ResponseCodeType;
  message?: string;
  showType?: ErrorShowType;
  additional: object;
}
/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const errorConfig: RequestConfig = {
  // 错误处理： umi@3 的错误处理方案。
  errorConfig: {
    // 错误抛出
    errorThrower: (res) => {
      const { code, message, showType, data } = res as unknown as ResponseStructure;
      if (code === ResponseCodeType.TOKEN_JWT_PARSING_FAILED) {
        Cookies.remove('token');
        const hasRedirect = history.location.pathname !== loginPath;
        history.replace(
          `${loginPath}${hasRedirect ? `?redirect=${history.location.pathname}` : ''}`,
        );
      } else if (code !== ResponseCodeType.SUCCESS) {
        const error: any = new Error(message);
        error.name = 'BizError';
        error.info = { code, message, showType, data };
        throw error; // 抛出自制的错误
      }
    },
    // 错误接收及处理
    errorHandler: (error: any, opts: any) => {
      if (opts?.skipErrorHandler) throw error;
      // 我们的 errorThrower 抛出的错误。
      if (error.name === 'BizError') {
        const errorInfo: ResponseStructure | undefined = error.info;
        if (errorInfo) {
          const { message: errorMessage } = errorInfo;
          message.error(errorMessage);
        }
      } else if (error.name === 'TokenError') {
        const hasRedirect = history.location.pathname !== loginPath;
        history.replace(
          `${loginPath}${hasRedirect ? `?redirect=${history.location.pathname}` : ''}`,
        );
      } else if (error.response) {
        // Axios 的错误
        // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
        message.error(`Response status:${error.response.status}`);
      } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        // \`error.request\` 在浏览器中是 XMLHttpRequest 的实例，
        // 而在node.js中是 http.ClientRequest 的实例
        message.error('None response! Please retry.');
      } else {
        // 发送请求时出了点问题
        message.error(error.message);
      }
    },
  },
  // 请求拦截器
  requestInterceptors: [
    (config: RequestOptions) => {
      if (PROXY_PREFIX) {
        config.url = `${PROXY_PREFIX}${config.url}`;
      }
      if (config.skipAuth !== true) {
        const Authorization = Cookies.get('Authorization');
        config.headers = {
          ...config.headers,
          Authorization: `Bearer ${Authorization!}`,
        };
      }
      // 拦截请求配置，进行个性化处理。
      return { ...config };
    },
  ],
  // 响应拦截器
  responseInterceptors: [
    (response) => {
      const responseData = response.data as API.ApiResponse;
      const { code } = responseData;
      if (responseData.code === ResponseCodeType.TOKEN_JWT_PARSING_FAILED) {
        responseData.success = false;
      } else if (code !== ResponseCodeType.SUCCESS && !responseData.success) {
        responseData.success = false;
      }
      return response;
    },
  ],
};
