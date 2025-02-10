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

import { Footer } from '@/components';
import { login } from '@/services/device-links/api';
import { getFakeCaptcha } from '@/services/device-links/login';
import {
  LockOutlined,
  MobileOutlined,
  UserOutlined,
} from '@ant-design/icons';
import md5 from 'crypto-js/md5';
import {
  LoginForm,
  ProFormCaptcha,
  ProFormCheckbox,
  ProFormText,
} from '@ant-design/pro-components';
import { history, useModel, Helmet } from '@umijs/max';
import { Alert, message, Tabs } from 'antd';
import Settings from '../../../../config/defaultSettings';
import React, { useState } from 'react';
import { flushSync } from 'react-dom';
import { createStyles } from 'antd-style';
const useStyles = createStyles(({ token }) => {
  return {
    action: {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    },
    lang: {
      width: 42,
      height: 42,
      lineHeight: '42px',
      position: 'fixed',
      right: 16,
      borderRadius: token.borderRadius,
      ':hover': {
        backgroundColor: token.colorBgTextHover,
      },
    },
    container: {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage:
        "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
      backgroundSize: '100% 100%',
    },
  };
});
const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};
const Login: React.FC = () => {
  const [userLoginState, setUserLoginState] = useState<API.LoginResult>({});
  const [type, setType] = useState<string>('account');
  const { initialState, setInitialState } = useModel('@@initialState');
  const { styles } = useStyles();
  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();
    if (userInfo) {
      flushSync(() => {
        setInitialState((s) => ({
          ...s,
          currentUser: userInfo,
        }));
      });
    }
  };
  const handleSubmit = async (values: API.LoginParams) => {
    if(values.password){
      values.password = md5(values.password).toString().substring(8, 24);
    }
    try {
      // 登录
      const loginResult = await login({
        ...values,
      },{
        skipErrorHandler:true
      });
      if (loginResult.success) {
        const defaultLoginSuccessMessage = '登录成功！';
        message.success(defaultLoginSuccessMessage);
        await fetchUserInfo();
        const urlParams = new URL(window.location.href).searchParams;
        history.push(urlParams.get('redirect') || '/');
        return;
      }
      // 如果失败去设置用户错误信息
      setUserLoginState(loginResult);
    } catch (error) {
      const defaultLoginFailureMessage = '登录失败，请重试！';
      message.error(error?.message|| defaultLoginFailureMessage);
    }
  };
  const { success, type: loginType } = userLoginState;
  return (
    <div className={styles.container}>
      <Helmet>
        <title>
          {'登录'}- {Settings.title}
        </title>
      </Helmet>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" src="/logo.svg" />}
          title="DeviceLinks"
          subTitle={'开箱即用的容器化模块物联网平台'}
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.LoginParams);
          }}
        >
          <Tabs
            activeKey={type}
            onChange={setType}
            centered
            items={[
              {
                key: 'account',
                label: '账户密码登录',
              },
              // {
              //   key: 'mobile',
              //   label: '手机号登录',
              // },
            ]}
          />
          {success===false && (
            <LoginMessage content={'错误的用户名和密码(admin/admin)'} />
          )}
          {type === 'account' && (
            <>
              <ProFormText
                name="username"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined />,
                }}
                placeholder={'用户名: 示例值 admin'}
                rules={[
                  {
                    required: true,
                    message: '用户名是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="password"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder={'密码: 示例值 admin'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                ]}
              />
            </>
          )}

          {status === 'error' && loginType === 'mobile' && <LoginMessage content="验证码错误" />}
          {type === 'mobile' && (
            <>
              <ProFormText
                fieldProps={{
                  size: 'large',
                  prefix: <MobileOutlined />,
                }}
                name="mobile"
                placeholder={'请输入手机号！'}
                rules={[
                  {
                    required: true,
                    message: '手机号是必填项！',
                  },
                  {
                    pattern: /^1\d{10}$/,
                    message: '不合法的手机号！',
                  },
                ]}
              />
              <ProFormCaptcha
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                captchaProps={{
                  size: 'large',
                }}
                placeholder={'请输入验证码！'}
                captchaTextRender={(timing, count) => {
                  if (timing) {
                    return `${count} ${'秒后重新获取'}`;
                  }
                  return '获取验证码';
                }}
                name="captcha"
                rules={[
                  {
                    required: true,
                    message: '验证码是必填项！',
                  },
                ]}
                onGetCaptcha={async (phone) => {
                  const result = await getFakeCaptcha({
                    phone,
                  });
                  if (!result) {
                    return;
                  }
                  message.success('获取验证码成功！验证码为：1234');
                }}
              />
            </>
          )}
          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              自动登录
            </ProFormCheckbox>
            <a
              style={{
                float: 'right',
              }}
            >
              忘记密码 ?
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};
export default Login;
