import React, { useState } from 'react';
import { ActionType, ProColumns, ProTable } from '@ant-design/pro-components';
import {
  deleteApiDeviceDeviceId,
  postApiDeviceFilter,
} from '@/services/device-links-console-ui/device';
import { useModel, useRequest } from '@umijs/max';
import { Button, message, Modal } from 'antd';
type ProductDeviceInfoProps = {
  /*产品Id*/
  productId?: string;
};
const ProductDeviceInfo: React.FC<ProductDeviceInfoProps> = ({ productId }) => {
  const { enums, getProSchemaValueEnumObjByEnum } = useModel('enumModel');
  const { DeviceType, DeviceStatus } = enums;
  const [messageApi, contextHolder] = message.useMessage();
  const [searchKeyword, setSearchKeyword] = useState<string>('');
  const deviceTableActionRef = React.useRef<ActionType>();
  const fetchProductDeviceData = async (
    params: API.postApiDeviceFilterParams & { pageSize?: number; current?: number },
  ) => {
    if (!productId)
      return {
        data: [],
        total: 0,
        success: true,
      };
    const { data } = await postApiDeviceFilter(
      {
        pageSize: params.pageSize,
        page: params.current,
      },
      {
        searchFieldModule: 'Device',
        searchMatch: 'ALL',
        searchFields: [
          {
            field: 'productId',
            operator: 'EqualTo',
            value: productId,
          },
          {
            field: 'deviceName',
            operator: 'Like',
            value: searchKeyword,
          },
        ],
      },
    );
    return {
      data: data.result || [],
      total: data.totalRows,
      success: true,
    };
  };
  const { run: deleteDevice } = useRequest(deleteApiDeviceDeviceId, {
    manual: true,
    onSuccess: () => {
      deviceTableActionRef.current?.reload();
      messageApi?.success('删除设备成功');
    },
  });
  const handleDel = (record: API.Device) => {
    Modal.confirm({
      title: '提示',
      content: '确定要删除该设备吗？',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        await deleteDevice({ deviceId: record.id });
      },
    });
  };
  const columns: ProColumns<API.Device>[] = [
    {
      dataIndex: 'id',
      title: '设备ID',
      width: 250,
    },
    {
      dataIndex: 'deviceName',
      title: '设备名称',
    },
    {
      dataIndex: 'deviceType',
      title: '设备类型',
      valueType: 'select',
      fieldProps: {
        options: DeviceType,
      },
    },
    {
      dataIndex: 'tags',
      title: '标签',
    },
    {
      dataIndex: 'status',
      title: '设备状态',
      valueEnum: getProSchemaValueEnumObjByEnum(DeviceStatus, true),
    },
    {
      dataIndex: 'lastOnlineTime',
      title: '最后上线时间',
    },
    {
      dataIndex: 'lastReportTime',
      title: '最后上报时间',
    },
    {
      dataIndex: 'mark',
      title: '备注',
    },
    {
      dataIndex: 'option',
      valueType: 'option',
      title: '操作',
      render: (_, record) => {
        return (
          <Button type="link" onClick={() => handleDel(record)} danger>
            删除
          </Button>
        );
      },
    },
  ];
  return (
    <>
      {contextHolder}
      <ProTable<API.Device & API.postApiDeviceFilterParams>
        columns={columns}
        rowKey={'id'}
        search={false}
        actionRef={deviceTableActionRef}
        request={fetchProductDeviceData}
        toolbar={{
          search: {
            placeholder: '请输入设备名称',
            allowClear: true,
            onSearch: (value) => {
              setSearchKeyword(value);
              deviceTableActionRef.current?.reload();
            },
          },
        }}
      ></ProTable>
    </>
  );
};
export default ProductDeviceInfo;
