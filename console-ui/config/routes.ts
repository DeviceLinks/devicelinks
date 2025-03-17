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

export default [
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './user/login' }],
  },
  { path: '/home', name: '首页', icon: 'home', component: './home' },
  {
    path: '/device',
    name: '设备管理',
    icon: 'laptop',
    routes: [
      { path: '/device', redirect: '/device/product' },
      {
        path: '/device/product',
        name: '产品',
        flatMenu: true,
        routes: [
          { path: '/device/product', redirect: '/device/product/list' },
          {
            path: '/device/product/list',
            name: '产品',
            component: './device/product/list',
          },
          {
            path: '/device/product/profile',
            name: '产品详情',
            hideInMenu: true,
            component: './device/product/profile',
          },
        ],
      },
    ],
  },
  {
    path: '/system',
    name: '系统管理',
    icon: 'setting',
    routes: [
      { path: '/system', redirect: '/system/user' },
      {
        path: '/system/user',
        name: '用户',
        routes: [
          { path: '/system/user', redirect: '/system/user/list' },
          {
            path: '/system/user/list',
            component: './system/user/list',
          },
        ],
      },
    ],
  },
  {
    path: '/list',
    icon: 'table',
    name: '列表页',
    routes: [
      {
        path: '/list/search',
        name: '搜索列表',
        component: './list/search',
        routes: [
          { path: '/list/search', redirect: '/list/search/articles' },
          {
            name: '搜索列表（文章）',
            icon: 'smile',
            path: '/list/search/articles',
            component: './list/search/articles',
          },
          {
            name: '搜索列表（项目）',
            icon: 'smile',
            path: '/list/search/projects',
            component: './list/search/projects',
          },
          {
            name: '搜索列表（应用）',
            icon: 'smile',
            path: '/list/search/applications',
            component: './list/search/applications',
          },
        ],
      },
      { path: '/list', redirect: '/list/table-list' },
      { name: '标准列表', icon: 'smile', path: '/list/basic-list', component: './list/basic-list' },
      { name: '卡片列表', icon: 'smile', path: '/list/card-list', component: './list/card-list' },
    ],
  },
  { path: '/', redirect: '/home' },
  { path: '*', layout: false, component: './404' },
];
