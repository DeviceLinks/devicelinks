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
      { path: '/device/product/list', name: '产品', component: './device/product/list' },
    ],
  },
  {
    path: '/system',
    name: '系统管理',
    icon: 'setting',
    routes: [
      { path: '/system', redirect: '/system/user' },
      { path: '/system/user/list', name: '用户', component: './system/user/list' },
    ],
  },
  { path: '/', redirect: '/home' },
  { path: '*', layout: false, component: './404' },
];
