import React from 'react';
import { PageContainer, ProTable, ProColumns } from '@ant-design/pro-components';
import { Button } from 'antd';
import { postApiProductFilter } from '@/services/device-links-console-ui/product';
import { useModel } from '@umijs/max';
import {FilterButtonBox} from "@/components/FilterButtonBox";

const Product: React.FC = () => {
  const { enums, getProSchemaValueEnumObjByEnum } = useModel('enumModel');
  const { DeviceType, ProductStatus,DeviceNetworkingAway,AccessGatewayProtocol } = enums!;
  const columns: ProColumns<API.Product>[] = [
    {
      title: '产品名称',
      dataIndex: 'name',
      sorter: true,
      key: 'name',
    },
    {
      title: '产品Key',
      dataIndex: 'productKey',
      search: false,
      key: 'productKey',
    },
    {
      title: '设备类型',
      dataIndex: 'deviceType',
      sorter: true,
      key: 'deviceType',
      valueType: 'select',
      fieldProps:{
        options: DeviceType,
      },
    },
    {
      title: '联网方式',
      dataIndex: 'networkingAway',
      sorter: true,
      valueType: 'select',
      fieldProps:{
        options: DeviceNetworkingAway,
      },
    },
    {
      title: '接入网关协议',
      dataIndex: 'accessGatewayProtocol',
      sorter: true,
      valueType: 'select',
      fieldProps:{
        options: AccessGatewayProtocol,
      },
    },
    {
      title: '状态',
      dataIndex: 'status',
      sorter: true,
      search: false,
      valueEnum: getProSchemaValueEnumObjByEnum(ProductStatus,true),
    },
    {
      title: '操作',
      valueType: 'option',
      render: () => {
        return (
          <>
            <Button type="link" onClick={() => {}} danger>
              删除
            </Button>
          </>
        );
      },
    },
  ];
  //获取表格数据
  const fetchData = async (
    params: API.postApiProductFilterParams & { pageSize?: number; current?: number },
  ) => {
    const { data } = await postApiProductFilter(
      {
        pageSize: params.pageSize,
        page: params.current,
      },
      {
        searchFieldModule: 'Product',
        searchMatch: 'ALL',
      },
    );
    return {
      data: data.result,
      total: data.totalRows,
      success: true,
    };
  };
  return (
    <PageContainer
      header={{
        title: '产品管理',
      }}
      content="维护不同厂商的设备产品列表"
    >
      <ProTable<API.Product, API.postApiProductFilterParams>
        columns={columns}
        search={false}
        toolBarRender={
          () => [
             // <Form></Form>,
             <FilterButtonBox key={'Product'} module={'Product'} confirm={()=>{}}></FilterButtonBox>
            ]
          }
        request={fetchData}
      ></ProTable>
    </PageContainer>
  );
};

export default Product;
