import { FilterButtonBox } from '@/components/FilterButtonBox';
import Add from '@/pages/system/user/modules/CreateUserForm';
import {
  deleteApiUserUserId,
  postApiUserFilter,
  postApiUserStatusUserId,
} from '@/services/device-links-console-ui/user';
import { ActionType, PageContainer, ProTable } from '@ant-design/pro-components';
import { history, useModel } from '@umijs/max';
import { Button, message, Modal } from 'antd';
import { SortOrder } from 'antd/es/table/interface';
import _ from 'lodash';
import React, { ReactNode, useRef, useState } from 'react';

const UserInfo: React.FC = () => {
  const { enums } = useModel('enumModel');
  const { UserActivateMethod } = enums!;
  const [loading, setLoading] = useState<boolean>(true);
  const initialSearchField: API.SearchFieldQuery = {
    searchFieldModule: 'User',
    searchMatch: 'ANY',
    searchFields: [],
  };
  const [searchField, setSearchField] = useState<API.SearchFieldQuery>(initialSearchField);
  const tableRef = useRef<ActionType>();

  /**启用/禁用 */
  const handleEnabled = async (record: API.User) => {
    const res: any = await postApiUserStatusUserId({
      userId: record.id,
      enabled: String(!record.enabled),
    });
    if (res.code === 'SUCCESS') {
      message.success(record.enabled ? '禁用成功' : '启用成功');
      tableRef.current?.reload();
    }
  };
  const confirmFilter = (val: API.SearchFieldQuery) => {
    setSearchField(val);
    tableRef.current?.reload();
  };
  /**删除用户 */
  const handleDel = (record: API.User) => {
    Modal.confirm({
      title: '提示',
      content: '确定要删除该用户吗？',
      okText: '确定',
      cancelText: '取消',
      onOk: () => {
        return new Promise((resolve: any, reject: any) => {
          try {
            deleteApiUserUserId({ userId: record.id })
              .then((result: any) => {
                if (result.code === 'SUCCESS') {
                  message.success('删除成功');
                  resolve();
                  tableRef.current?.reload();
                }
              })
              .catch(() => {
                reject();
              });
          } catch (error) {}
        });
      },
    });
    console.log(record);
  };

  /**
   * 表格操作按钮
   * @param _text
   * @param record
   */
  const operationBtnGroup = (_text: ReactNode, record: API.User) => {
    return (
      <>
        <Button type="link" danger onClick={() => handleDel(record)}>
          删除
        </Button>
        {record.enabled ? (
          <Button type="link" danger onClick={() => handleEnabled(record)}>
            禁用
          </Button>
        ) : (
          <Button type="link" onClick={() => handleEnabled(record)}>
            启用
          </Button>
        )}
      </>
    );
  };

  /**
   * 输入框
   */
  const handleSearchInput = (value: string) => {
    console.log(value);
    const filterInputParams: API.SearchFieldQuery = {
      searchFieldModule: 'User',
      searchMatch: 'ANY',
      searchFields: [
        {
          field: 'name',
          operator: 'Like',
          value: value,
        },
        {
          field: 'account',
          operator: 'Like',
          value: value,
        },
        {
          field: 'phone',
          operator: 'Like',
          value: value,
        },
      ],
    };
    setSearchField(filterInputParams);
    tableRef.current?.reload();
  };

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
      searchField,
    );
    setLoading(false);
    return {
      data: result.data.result,
      success: true,
      total: result.data.totalRows,
    };
  };

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
    { title: '状态', dataIndex: 'xxxx', ellipsis: true },
    {
      title: '最后登录时间',
      dataIndex: 'lastLoginTime',
      ellipsis: true,
      sorter: true,
      render: (_: any, record: API.User) => {
        return record.lastLoginTime?.replace('T', ' ') ?? '-';
      },
    },
    {
      title: '新增时间',
      dataIndex: 'createTime',
      ellipsis: true,
      sorter: true,
      render: (_: any, record: API.User) => {
        return record.createTime?.replace('T', ' ') ?? '-';
      },
    },
    { title: '操作', dataIndex: 'operation', ellipsis: true, render: operationBtnGroup },
  ];

  return (
    <PageContainer
      title={'用户管理'}
      content={'当前用户池的所有用户，在这里你可以对用户进行统一管理'}
      extra={<Add refresh={() => tableRef.current?.reload()} btnType={'primary'} />}
    >
      <ProTable<API.User, API.postApiUserFilterParams & API.SearchField>
        actionRef={tableRef}
        loading={loading}
        columns={TABLE_COLUMNS}
        rowKey={(record) => record.id}
        search={false}
        toolbar={{
          actions: [
            <FilterButtonBox
              key={'User'}
              initialValues={initialSearchField}
              confirm={confirmFilter}
            ></FilterButtonBox>,
          ],
          search: {
            placeholder: '请输入名称/账号/手机号进行搜索数据',
            allowClear: true,
            onSearch: handleSearchInput,
            style: {
              width: '350px',
            },
          },
        }}
        request={fetchData}
      />
    </PageContainer>
  );
};

export default UserInfo;
