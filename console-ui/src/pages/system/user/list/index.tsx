import { FilterButtonBox } from '@/components/FilterButtonBox';
import Add from '@/pages/system/user/modules/add';
import {
  deleteApiUserUserId,
  postApiUserFilter,
  postApiUserStatusUserId,
} from '@/services/device-links-console-ui/user';
import  { SearchOutlined } from '@ant-design/icons';
import { ActionType, PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, Form, Input, message, Modal } from 'antd';
import React, { ReactNode, useRef, useState } from 'react';

const UserInfo: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(true);
  const [filterBoxVlaues, setFilterBoxVlaues] = useState<any>();
  const tableRef = useRef<ActionType>();
  const [contion] = Form.useForm();
  /**
   *
   */
  const handleFilter = (val) => {
    setFilterBoxVlaues(val);
    tableRef.current?.reload();
  };

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
   * 表格列设置
   */
  const TABLE_COLUMNS = [
    {
      title: '名称',
      dataIndex: 'name',
      ellipsis: true,
      render: (_: any, record: API.User) => {
        return <Button type="link">{record.name}</Button>;
      },
    },
    { title: '账号', dataIndex: 'account', ellipsis: true },
    { title: '手机号', dataIndex: 'phone', ellipsis: true },
    { title: '激活方式', dataIndex: 'activateMethod', ellipsis: true },
    { title: '邮箱', dataIndex: 'email', ellipsis: true },
    { title: '状态', dataIndex: 'xxxx', ellipsis: true },
    {
      title: '最后登录时间',
      dataIndex: 'lastLoginTime',
      ellipsis: true,
      render: (_: any, record: API.User) => {
        return record.lastLoginTime?.replace('T', ' ') ?? '-';
      },
    },
    {
      title: '新增时间',
      dataIndex: 'createTime',
      ellipsis: true,
      render: (_: any, record: API.User) => {
        return record.createTime?.replace('T', ' ') ?? '-';
      },
    },
    { title: '操作', dataIndex: 'operation', ellipsis: true, render: operationBtnGroup },
  ];

  /**
   * 搜索
   */
  const searchContainer = ()=>{
    return(
      <Form
        form={contion}
        name={'contion'}
        layout={'inline'}
        style={{ marginBottom: 15 }}
        variant={'filled'}
      >
        <Form.Item>
          <Input prefix={<SearchOutlined />} placeholder="搜索" />

        </Form.Item>
        <FilterButtonBox
          module={'User'}
          confirm={(val) => handleFilter(val)}
        ></FilterButtonBox>
      </Form>
    )
  }
  return (
    <PageContainer
      title={'用户管理'}
      content={'当前用户池的所有用户，在这里你可以对用户进行统一管理'}
      extra={<Add refresh={() => tableRef.current?.reload()} />}
    >
        {searchContainer()}
        <ProTable<API.User, API.postApiUserFilterParams>
          actionRef={tableRef}
          loading={loading}
          columns={TABLE_COLUMNS}
          search={false}
          request={async (
            params: API.postApiUserFilterParams & { pageSize?: number; current?: number },
          ) => {
            setLoading(true);
            const result: any = await postApiUserFilter(
              {
                page: params.current,
                pageSize: params.pageSize,
              },
              {
                searchFields: filterBoxVlaues,
                searchFieldModule: 'User',
                searchMatch: 'ANY',
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
    </PageContainer>
  );
};

export default UserInfo;
