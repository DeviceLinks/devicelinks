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

import { history } from '@umijs/max';
import { Button, Result } from 'antd';
import React from 'react';
const NoFoundPage: React.FC = () => (
  <Result
    status="404"
    title="404"
    subTitle={'抱歉，您访问的页面不存在。'}
    extra={
      <Button type="primary" onClick={() => history.push('/')}>
        {'返回首页'}
      </Button>
    }
  />
);
export default NoFoundPage;
