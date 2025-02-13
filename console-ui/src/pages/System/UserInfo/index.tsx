import { PageContainer } from '@ant-design/pro-components';
import React from 'react';
import { Button } from 'antd';

const UserInfo: React.FC = () => {
  return (
    <PageContainer
      title={'用户管理'}
      content={'当前用户池的所有用户，在这里你可以对用户进行统一管理'}
      extra={[
        <Button key="add" type="primary">
          成员入职
        </Button>,
      ]}
    ></PageContainer>
  );
};

export default UserInfo;
