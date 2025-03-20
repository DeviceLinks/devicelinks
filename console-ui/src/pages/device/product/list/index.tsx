import React from 'react';
import { PageContainer, ProTable, ProColumns, ActionType } from '@ant-design/pro-components';
import { Button, Form, Input, message, Modal, Select } from 'antd';
import {
  deleteApiProductProductId,
  postApiProductFilter,
  postApiProductProductIdPublish,
} from '@/services/device-links-console-ui/product';
import { useModel, useRequest } from '@umijs/max';
import { FilterButtonBox } from '@/components/FilterButtonBox';
import { SortOrder } from 'antd/es/table/interface';
import _ from 'lodash';
import CreateProductForm from '@/pages/device/product/modules/CreateProductForm';
const Product: React.FC = () => {
  const { enums, getProSchemaValueEnumObjByEnum } = useModel('enumModel');
  const { DeviceType, ProductStatus, DeviceNetworkingAway, AccessGatewayProtocol } = enums;
  const tableActionRef = React.useRef<ActionType>();
  const [messageApi, contextHolder] = message.useMessage();
  const { run: deleteProduct } = useRequest(deleteApiProductProductId, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('删除产品成功');
      tableActionRef.current?.reload();
    },
  });
  const { run: publishProduct } = useRequest(postApiProductProductIdPublish, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('发布产品成功');
      tableActionRef.current?.reload();
    },
  });
  const handleDel = (record: API.Product) => {
    Modal.confirm({
      title: '提示',
      content: '确定要删除该产品吗？',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        await deleteProduct({ productId: record.id });
      },
    });
  };
  const handlePublish = (record: API.Product) => {
    Modal.confirm({
      title: '提示',
      content: '确定要发布该产品吗？',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        await publishProduct({ productId: record.id });
      },
    });
  };
  const columns: ProColumns<API.Product>[] = [
    {
      title: '产品名称',
      dataIndex: 'name',
      search: false,
      sorter: true,
      key: 'name',
      render: (_, record: API.Product) => {
        return (
          <Button type="link" href={`#/device/product/profile/${record.id}`}>
            {record.name}
          </Button>
        );
      },
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
      fieldProps: {
        options: DeviceType,
      },
    },
    {
      title: '联网方式',
      dataIndex: 'networkingAway',
      key: 'networkingAway',
      sorter: true,
      valueType: 'select',
      fieldProps: {
        options: DeviceNetworkingAway,
      },
    },
    {
      title: '接入网关协议',
      dataIndex: 'accessGatewayProtocol',
      key: 'accessGatewayProtocol',
      sorter: true,
      valueType: 'select',
      fieldProps: {
        options: AccessGatewayProtocol,
      },
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      sorter: true,
      search: false,
      valueEnum: getProSchemaValueEnumObjByEnum(ProductStatus, true),
    },
    {
      title: '新增时间',
      dataIndex: 'createTime',
      key: 'createTime',
      valueType: 'dateTime',
      sorter: true,
    },
    {
      title: '操作',
      valueType: 'option',
      key: 'option',
      render: (_, record) => {
        return (
          <>
            <Button type="link" onClick={() => handleDel(record)} danger>
              删除
            </Button>
            {record.status !== 'Published' && (
              <Button type="link" onClick={() => handlePublish(record)}>
                发布
              </Button>
            )}
          </>
        );
      },
    },
  ];
  const [searchForm] = Form.useForm<API.Product>();
  const customSearchFields = Form.useWatch((value) => {
    const fields: API.SearchFieldItem[] = [];
    if (value.name) {
      fields.push({
        field: 'name',
        operator: 'Like',
        value: value.name,
      });
    }
    if (value.deviceType) {
      fields.push({
        field: 'deviceType',
        operator: 'EqualTo',
        value: value.deviceType,
      });
    }
    if (value.accessGatewayProtocol) {
      fields.push({
        field: 'accessGatewayProtocol',
        operator: 'EqualTo',
        value: value.accessGatewayProtocol,
      });
    }
    if (value.networkingAway) {
      fields.push({
        field: 'networkingAway',
        operator: 'EqualTo',
        value: value.networkingAway,
      });
    }
    return fields;
  }, searchForm);

  const initialSearchField: API.SearchField = {
    searchFieldModule: 'Product',
    searchMatch: 'ANY',
    searchFields: [],
  };
  const [searchField, setSearchField] = React.useState<API.SearchField>(initialSearchField);
  //获取表格数据
  const fetchData = async (
    params: API.postApiProductFilterParams & { pageSize?: number; current?: number },
    sort: Record<string, SortOrder>,
  ) => {
    const { data } = await postApiProductFilter(
      {
        pageSize: params.pageSize,
        page: params.current,
        ...(sort && !_.isEmpty(sort)
          ? {
              sortProperty: Object.keys(sort)[0], // 排序字段
              sortDirection: sort[Object.keys(sort)[0]] === 'ascend' ? 'ASC' : 'DESC', // 排序顺序
            }
          : {}),
      },
      {
        ...searchField,
        searchFields: [...(searchField.searchFields || []), ...customSearchFields],
      },
    );
    return {
      data: data.result || [],
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
      extra={<CreateProductForm key="create" reload={tableActionRef.current?.reload} />}
    >
      {contextHolder}
      <ProTable<API.Product, API.postApiProductFilterParams & API.SearchField>
        columns={columns}
        params={searchField}
        rowKey="id"
        search={false}
        toolbar={{
          search: (
            <>
              <Form<API.Product>
                key={'search'}
                layout="inline"
                form={searchForm}
                onValuesChange={(changedValues) => {
                  // 产品名称更新时，不立即刷新
                  if (!changedValues.name) {
                    tableActionRef.current?.reload();
                  }
                }}
              >
                <Form.Item name="name">
                  <Input.Search
                    placeholder="请输入产品名称进行搜索数据"
                    allowClear
                    onSearch={() => {
                      tableActionRef.current?.reload();
                    }}
                  />
                </Form.Item>
                <Form.Item name="deviceType">
                  <Select
                    placeholder="请选择设备类型"
                    allowClear={true}
                    options={DeviceType}
                  ></Select>
                </Form.Item>
                <Form.Item name="networkingAway">
                  <Select
                    placeholder="请选择联网方式"
                    allowClear={true}
                    options={DeviceNetworkingAway}
                  ></Select>
                </Form.Item>
                <Form.Item name="accessGatewayProtocol">
                  <Select
                    placeholder="请选择接入网关协议"
                    allowClear={true}
                    options={AccessGatewayProtocol}
                  ></Select>
                </Form.Item>
              </Form>
            </>
          ),
          actions: [
            <FilterButtonBox
              key={'Product'}
              initialValues={initialSearchField}
              confirm={setSearchField}
            ></FilterButtonBox>,
          ],
        }}
        request={fetchData}
        actionRef={tableActionRef}
      ></ProTable>
    </PageContainer>
  );
};

export default Product;
