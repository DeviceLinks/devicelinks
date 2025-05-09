import { postApiUserFilter } from '@/services/device-links-console-ui/user';
import { history, useModel } from '@@/exports';
import { PlusOutlined, TeamOutlined } from '@ant-design/icons';
import { ActionType, ProCard, ProTable } from '@ant-design/pro-components';
import { Button } from 'antd';
import { SortOrder } from 'antd/es/table/interface';
import React, { useRef, useState } from 'react';

interface Props {
  department: API.DepartmentTree | undefined;
}

const UserContent: React.FC<Props> = ({ department }) => {
  const { enums } = useModel('enumModel');
  const { UserActivateMethod } = enums!;
  const [loading, setLoading] = useState<boolean>(true);
  const tableRef = useRef<ActionType>();
  const title = (
    <div>
      {department?.name}
      <TeamOutlined style={{ color: '#979797', marginLeft: '10px' }} />
    </div>
  );
  /**
   * 表格列设置
   */
  const TABLE_COLUMNS = [
    {
      title: '名称',
      dataIndex: 'name',
      ellipsis: true,
      sorter: true,
      render: (_: any, record: API.User) => {
        return (
          <Button
            type="link"
            onClick={() => history.push(`/system/user/profile?userId=${record.id}`)}
          >
            {record.name}
          </Button>
        );
      },
    },
    { title: '账号', dataIndex: 'account', ellipsis: true, sorter: true },
    { title: '手机号', dataIndex: 'phone', ellipsis: true, sorter: true },
    {
      title: '激活方式',
      dataIndex: 'activateMethod',
      ellipsis: true,
      sorter: true,
      fieldProps: {
        options: UserActivateMethod,
      },
    },
    { title: '邮箱', dataIndex: 'email', ellipsis: true, sorter: true },
    // { title: '操作', dataIndex: 'operation', ellipsis: true, render: operationBtnGroup },
  ];
  /**
   * 查询数据
   */
  const fetchData = async (
    params: API.postApiUserFilterParams & { pageSize?: number; current?: number },
    sort: Record<string, SortOrder>,
  ) => {
    setLoading(true);
    const result: any = await postApiUserFilter(
      {
        page: params.current,
        pageSize: params.pageSize,
        ...(sort && !_.isEmpty(sort)
          ? {
              sortProperty: Object.keys(sort)[0], // 排序字段
              sortDirection: sort[Object.keys(sort)[0]] === 'ascend' ? 'ASC' : 'DESC', // 排序顺序
            }
          : {}),
      },
      {
        searchFieldModule: 'User',
        searchMatch: 'ANY',
        searchFields: [],
      },
    );
    setLoading(false);
    return {
      data: result.data.result,
      success: true,
      total: result.data.totalRows,
    };
  };
  return (
    <ProCard
      title={title}
      extra={
        <Button type={'link'} icon={<PlusOutlined />}>
          添加成员
        </Button>
      }
      headerBordered
    >
      <ProTable<API.User, API.postApiUserFilterParams & API.SearchField>
        loading={loading}
        actionRef={tableRef}
        columns={TABLE_COLUMNS}
        rowKey={(record) => record.id}
        search={false}
        request={fetchData}
      />
    </ProCard>
  );
};
export default UserContent;
