import UserInfo from '@/pages/system/user/profile/modules/UserInfo';
import { getApiUserUserId } from '@/services/device-links-console-ui/user';
import { useSearchParams } from '@@/exports';
import { PageContainer } from '@ant-design/pro-components';
import { history } from '@umijs/max';
import { message, Tabs } from 'antd';
import React from 'react';

const UserProfile = () => {
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
  React.useEffect(() => {
    getUserInfo();
  }, []);
  const tabItems = [
    {
      label: '用户信息',
      key: 'userInfo',
      children: <UserInfo userInfo={user} />,
    },
    {
      label: '审计日志',
      key: 'auditLog',
      children: 'Tab 2',
    },
  ];
  return (
    <PageContainer
      loading={loading}
      ghost
      header={{
        title: user?.name,
        breadcrumb: {},
      }}
    >
      <Tabs defaultActiveKey="userInfo" items={tabItems} />
    </PageContainer>
  );
};

export default UserProfile;
