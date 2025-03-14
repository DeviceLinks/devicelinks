import { FilterButtonBox } from '@/components/FilterButtonBox';
import Add from '@/pages/system/user/modules/add';
import {
  deleteApiUserUserId,
  postApiUserFilter,
  postApiUserStatusUserId,
} from '@/services/device-links-console-ui/user';
import { ActionType, PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, Form, Input, message, Modal } from 'antd';
import React, { ReactNode, useRef, useState } from 'react';
import {SortOrder} from "antd/es/table/interface";
import _ from "lodash";

const UserInfo: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const initialSearchField: API.SearchField = {
    searchFieldModule: 'User',
    searchMatch: 'ANY',
    searchFields: [],
  };
  const [searchField, setSearchField] = useState<API.SearchField>(initialSearchField);
  const tableRef = useRef<ActionType>();
  const [condition] = Form.useForm();

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
  const confirmFilter = (val: API.SearchField) => {
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
    const filterInputParams:API.SearchField = {
      searchFieldModule: 'User',
      searchMatch: 'ANY',
      searchFields: [
        {
          field: "name",
          operator: "Like",
          value: value,
        },{
          field: "account",
          operator: "Like",
          value: value,
        },{
          field: "phone",
          operator: "Like",
          value: value,
        }
      ],
    }
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
            sortOrder: sort[Object.keys(sort)[0]] === 'ascend' ? 'ASC' : 'DESC', // 排序顺序
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
        return <Button type="link">{record.name}</Button>;
      },
    },
    { title: '账号', dataIndex: 'account', ellipsis: true, sorter: true, },
    { title: '手机号', dataIndex: 'phone', ellipsis: true, sorter: true, },
    { title: '激活方式', dataIndex: 'activateMethod', ellipsis: true, sorter: true, },
    { title: '邮箱', dataIndex: 'email', ellipsis: true, sorter: true, },
    { title: '状态', dataIndex: 'xxxx', ellipsis: true, },
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
      extra={<Add refresh={() => tableRef.current?.reload()} />}
    >
      <ProTable<API.User, API.postApiUserFilterParams & API.SearchField>
        actionRef={tableRef}
        loading={loading}
        columns={TABLE_COLUMNS}
        rowKey={record => record.id}
        search={false}
        toolbar={{
          actions: [
            <FilterButtonBox
              key={'User'}
              initialValues={initialSearchField}
              confirm={confirmFilter}
            ></FilterButtonBox>,
          ],
          search: (
            <>
              <Form<API.User> layout="inline" form={condition}>
                <Form.Item name={'keyWord'}>
                  <Input.Search
                    placeholder="请输入名称/账号/手机号进行搜索数据"
                    allowClear={true}
                    style={{ width: '350px' }}
                    onSearch={handleSearchInput}
                  />
                </Form.Item>
              </Form>
            </>
          ),
        }}
        request={fetchData}
      />
    </PageContainer>
  );
};

export default UserInfo;
