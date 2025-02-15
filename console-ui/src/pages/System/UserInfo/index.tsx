import { PageContainer, ProCard, ProTable } from '@ant-design/pro-components';
import React, { useState } from 'react';
import { pageUserApi } from './api';
import Add from './modules/add';
const UserInfo: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(true);
  return (
    <PageContainer
      title={'用户管理'}
      content={'当前用户池的所有用户，在这里你可以对用户进行统一管理'}
      extra={<Add />}
    >
      <ProCard direction="column" ghost gutter={[0, 16]}>
        <ProTable
          loading={loading}
          // columns={{ TABLE_COLUMNS }}
          // params 是需要自带的参数
          // 这个参数优先级更高，会覆盖查询表单的参数
          // params={params}
          request={async (
            // 第一个参数 params 查询表单和 params 参数的结合
            // 第一个参数中一定会有 pageSize 和  current ，这两个参数是 antd 的规范
            params: {
              pageSize: number;
              current: number;
            },
          ) => {
            setLoading(true);
            const result: any = await pageUserApi(
              {
                page: params.current,
                pageSize: params.pageSize,
              },
              {
                searchFieldModule: 'User',
              },
            );
            setLoading(false);
            return {
              data: result.data.result,
              success: true,
              total: result.data.totalRows,
            };
          }}
        />
      </ProCard>
    </PageContainer>
  );
};

export default UserInfo;
