import UserInfo from '@/pages/system/user/profile/modules/UserInfo';
import {
  deleteApiUserUserId,
  getApiUserUserId,
  postApiUserStatusUserId,
  postApiUserUserId,
} from '@/services/device-links-console-ui/user';
import { useSearchParams } from '@@/exports';
import {
  CheckCircleOutlined,
  DeleteOutlined,
  DisconnectOutlined,
  DownOutlined,
  RollbackOutlined,
  SendOutlined,
  StopOutlined,
} from '@ant-design/icons';
import { PageContainer, ProSkeleton } from '@ant-design/pro-components';
import { history } from '@umijs/max';
import { Button, Dropdown, MenuProps, message, Modal, Space, Tabs, Tag, Typography } from 'antd';
import React from 'react';

const UserProfile = () => {
  const { Paragraph } = Typography;
  const [user, setUser] = React.useState<API.User>();
  const [loading, setLoading] = React.useState(false);
  const [params] = useSearchParams();
  const getUserInfo = async () => {
    if (!params.get('userId')) {
      message.error('没有要查询的用户ID，已返回用户列表');
      history.push('/system/user/list');
      return;
    }
    setLoading(true);
    try {
      const { data } = await getApiUserUserId({ userId: params.get('userId') ?? '' });
      setUser(data);
    } finally {
      setLoading(false);
    }
  };
  const menuItems: MenuProps['items'] = [
    user?.enabled
      ? {
          key: 'enabled',
          icon: <CheckCircleOutlined />,
          label: '启用用户',
        }
      : {
          key: 'disabled',
          icon: <StopOutlined />,
          label: '禁用用户',
        },
    {
      key: 'delete',
      icon: <DeleteOutlined />,
      label: '删除用户',
    },
    {
      key: 'forceOutline',
      icon: <DisconnectOutlined />,
      label: '强制下线',
    },

    {
      key: 'resetPwd',
      icon: <RollbackOutlined />,
      label: '重置密码',
      disabled: !user?.email,
    },
    {
      key: 'sendResetPwdLink',
      icon: <SendOutlined />,
      label: '发送用户重置密码链接',
      disabled: !user?.email,
    },
  ];
  const handleMenuClick: MenuProps['onClick'] = (e) => {
    if (!user || user.id) {
      message.error('暂无可操作用户');
      return;
    }
    if (e.key === 'delete') {
      Modal.confirm({
        title: '提示',
        content: '确定要删除该用户吗？',
        okText: '确定',
        cancelText: '取消',
        onOk: async () => {
          await deleteApiUserUserId({
            userId: user?.id,
          } as API.deleteApiUserUserIdParams);
        },
      });
    } else if (e.key === 'enable') {
      Modal.confirm({
        title: '提示',
        content: '确定要启用该用户吗？',
        okText: '确定',
        cancelText: '取消',
        onOk: async () => {
          await postApiUserStatusUserId({
            userId: user?.id,
            enabled: String(!user?.enabled),
          });
        },
      });
    } else if (e.key === 'disable') {
      Modal.confirm({
        title: '提示',
        content: '确定要禁用该用户吗？',
        okText: '确定',
        cancelText: '取消',
        onOk: async () => {
          await postApiUserStatusUserId({
            userId: user?.id,
            enabled: String(!user?.enabled),
          });
        },
      });
    } else {
      message.warning('暂不支持此功能');
    }
  };
  React.useEffect(() => {
    getUserInfo();
  }, []);
  const tabItems = [
    {
      label: '用户信息',
      key: 'userInfo',
      children: (
        <UserInfo
          initialValues={user}
          onSubmit={async (value) => {
            await postApiUserUserId({ userId: user?.id ?? '' }, {
              ...value,
            } as API.UpdateUserRequest);
            message.success('保存成员信息成功');
            getUserInfo();
          }}
        />
      ),
    },
    {
      label: '审计日志',
      key: 'auditLog',
      children: 'Tab 2',
    },
  ];
  if (loading) {
    return <ProSkeleton type="descriptions" pageHeader={false} />;
  }
  return (
    <PageContainer
      loading={loading}
      ghost
      header={{
        title: `用户名称：${user?.name}`,
      }}
      tags={
        user && (
          <Tag color={user.enabled ? 'success' : 'error'}>{user.enabled ? '启用中' : '禁用中'}</Tag>
        )
      }
      content={
        <Paragraph
          copyable={{
            text: `${user?.id}`,
          }}
        >
          ID：{user?.id || '-'}
        </Paragraph>
      }
      extra={
        <Dropdown menu={{ items: menuItems, onClick: handleMenuClick }}>
          <Button type={'primary'}>
            <Space>
              更多
              <DownOutlined />
            </Space>
          </Button>
        </Dropdown>
      }
    >
      <Tabs defaultActiveKey="userInfo" items={tabItems} />
    </PageContainer>
  );
};

export default UserProfile;
