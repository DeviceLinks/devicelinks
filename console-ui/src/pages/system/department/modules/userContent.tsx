import BatchAddUser from '@/pages/system/department/modules/batchAddUser';
import {
  deleteApiUserUserId,
  postApiUserFilter,
  postApiUserStatusUserId,
} from '@/services/device-links-console-ui/user';
import { history, useModel } from '@@/exports';
import { PlusOutlined, TeamOutlined } from '@ant-design/icons';
import { ActionType, ProCard, ProTable } from '@ant-design/pro-components';
import { Button, message, Modal } from 'antd';
import { SortOrder } from 'antd/es/table/interface';
import _ from 'lodash';
import React, { ReactNode, useRef, useState } from 'react';

interface Props {
  department: API.DepartmentTree | undefined;
}

const UserContent: React.FC<Props> = ({ department }) => {
  const { enums } = useModel('enumModel');
  const { UserActivateMethod } = enums!;
  const [loading, setLoading] = useState<boolean>(true);
  const [addUserModalVisible, setAddUserModalVisible] = useState<boolean>(false);
  const [total, setTotal] = useState(0);
  const tableRef = useRef<ActionType>();
  const title = (
    <div>
      {department?.id ? department?.name : '所有成员'}
      <span style={{ color: '#979797', marginLeft: '10px', fontSize: '12px' }}>
        <TeamOutlined style={{ marginRight: '5px' }} />
        {total}人
      </span>
    </div>
  );

  const cancelAddUser = () => {
    setAddUserModalVisible(false);
  };

  const handleAddUser = () => {
    setAddUserModalVisible(true);
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
    {
      title: '操作',
      dataIndex: 'operation',
      width: 140,
      ellipsis: true,
      render: operationBtnGroup,
    },
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
        searchFields: [
          {
            field: 'departmentId',
            operator: 'EqualTo',
            value: department?.id ? department?.id : '',
          },
        ],
      },
    );
    setLoading(false);
    setTotal(result.data.totalRows);
    return {
      data: result.data.result,
      success: true,
      total: result.data.totalRows,
    };
  };
  React.useEffect(() => {
    tableRef.current?.reload();
  }, [department]);
  return (
    <>
      <ProCard
        title={title}
        extra={
          <Button
            type={'link'}
            icon={<PlusOutlined />}
            onClick={() => handleAddUser()}
            disabled={department?.id ? false : true}
          >
            添加成员
          </Button>
        }
        headerBordered
      >
        <ProTable<API.User, API.postApiUserFilterParams & API.SearchField>
          loading={loading}
          actionRef={tableRef}
          columns={TABLE_COLUMNS}
          manualRequest={true}
          rowKey={(record) => record.id}
          search={false}
          request={fetchData}
          toolBarRender={false}
        />
      </ProCard>
      <BatchAddUser
        open={addUserModalVisible}
        onCancel={cancelAddUser}
        departmentInfo={department}
        refresh={() => tableRef.current?.reload()}
      />
    </>
  );
};
export default UserContent;
