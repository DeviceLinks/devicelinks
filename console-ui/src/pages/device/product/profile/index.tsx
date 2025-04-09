import React, { useState } from 'react';
import RcResizeObserver from 'rc-resize-observer';
import {
  PageContainer,
  ProForm,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  ProSkeleton,
  ProCard,
  ProList,
  ActionType,
  ProTable,
  ProColumns,
} from '@ant-design/pro-components';
import { useModel, useParams, useRequest } from '@umijs/max';
import {
  deleteApiProductProductId,
  getApiProductProductId,
  postApiProductProductId,
  postApiProductProductIdPublish,
} from '@/services/device-links-console-ui/product';
import { DeleteOutlined, DownOutlined, UploadOutlined } from '@ant-design/icons';
import { Button, Dropdown, MenuProps, message, Modal, Space, Tag } from 'antd';
import { Typography } from 'antd';
import {
  deleteApiFunctionModuleModuleId,
  postApiFunctionModuleFilter,
} from '@/services/device-links-console-ui/functionModule';
import CreateFunctionModuleForm from '@/pages/device/product/modules/CreateFunctionModuleForm';
import { MessageInstance } from 'antd/lib/message/interface';
import UpdateFunctionModuleForm from '@/pages/device/product/modules/UpdateFunctionModuleForm';
import { postApiAttributeFilter } from '@/services/device-links-console-ui/attribute';
import CreateAttributeForm from '@/pages/device/product/modules/CreateAttributeForm';
const { Paragraph } = Typography;
type ProductBaseInfoProps = {
  productInfo: API.Product | undefined;
  submit: (values: API.Product) => Promise<void>;
  enums: API.Enum;
};
/**
 * 产品基本信息
 * @param productInfo
 * @param submit
 * @param enums
 * @constructor
 */
const ProductBasicInfoForm: React.FC<ProductBaseInfoProps> = ({ productInfo, submit, enums }) => {
  const { DeviceType, ProductStatus, DeviceNetworkingAway, DeviceAuthenticationMethod } = enums;
  return (
    <ProForm<API.Product>
      initialValues={productInfo}
      onFinish={async (values) => {
        await submit(values);
      }}
      grid={true}
      rowProps={{
        gutter: 16,
      }}
      colProps={{
        xs: 24,
        sm: 24,
        md: 12,
        xl: 8,
        lg: 8,
      }}
    >
      <ProFormText
        name="id"
        label="产品ID"
        disabled
        fieldProps={{
          suffix: (
            <Paragraph
              style={{
                margin: 0,
              }}
              copyable={{
                text: productInfo?.id,
              }}
            ></Paragraph>
          ),
        }}
      ></ProFormText>
      <ProFormText name="name" label="产品名称" required></ProFormText>
      <ProFormText
        name="productKey"
        label="产品Key"
        disabled
        fieldProps={{
          suffix: (
            <Paragraph
              style={{
                margin: 0,
              }}
              copyable={{
                text: productInfo?.productKey,
              }}
            ></Paragraph>
          ),
        }}
      ></ProFormText>
      <ProFormText
        name="productSecret"
        label="产品Secret"
        disabled
        fieldProps={{
          suffix: (
            <Paragraph
              style={{
                margin: 0,
              }}
              copyable={{
                text: productInfo?.productSecret,
              }}
            ></Paragraph>
          ),
        }}
      ></ProFormText>
      <ProFormSelect
        name="deviceType"
        label="设备类型"
        options={DeviceType}
        disabled={productInfo?.status === 'Published'}
      ></ProFormSelect>
      <ProFormSelect
        name="networkingAway"
        label="联网方式"
        options={DeviceNetworkingAway}
        disabled={productInfo?.status === 'Published'}
      ></ProFormSelect>
      {productInfo?.authenticationMethod === 'ProductCredential' && (
        <ProFormSelect
          name="authenticationMethod"
          label="鉴权方式"
          options={DeviceAuthenticationMethod}
        ></ProFormSelect>
      )}
      <ProFormSelect name="status" label="状态" options={ProductStatus} disabled></ProFormSelect>
      <ProFormText name="createTime" label="创建时间" disabled></ProFormText>
      <ProFormTextArea
        name="description"
        colProps={{
          span: 24,
        }}
        label="描述"
      ></ProFormTextArea>
    </ProForm>
  );
};
type ProductFunctionModuleInfoProps = {
  productId: string | undefined;
  responsive: boolean;
  messageApi?: MessageInstance;
};

/**
 * 产品功能模块
 * @param productId
 * @param responsive
 * @param messageApi
 * @constructor
 */
const ProductFunctionModuleInfo: React.FC<ProductFunctionModuleInfoProps> = ({
  productId,
  responsive,
  messageApi,
}) => {
  const [currentModel, setCurrentModel] = useState<API.FunctionModule>();
  const functionModuleActionRef = React.useRef<ActionType>();
  /**
   * 获取功能模块列表
   */
  const fetchFunctionModuleData = async () => {
    const res = await postApiFunctionModuleFilter({
      searchFieldModule: 'FunctionModule',
      searchMatch: 'ALL',
      searchFields: [
        {
          field: 'productId',
          operator: 'EqualTo',
          value: productId,
        },
      ],
    } as API.SearchFieldQuery);
    const { data } = res;
    const defaultModule = data?.find((item) => item.identifier === 'default');
    if (defaultModule && !currentModel) setCurrentModel(defaultModule);
    return res;
  };
  /**
   * 删除功能模块API
   */
  const { run: deleteFunctionModule } = useRequest(deleteApiFunctionModuleModuleId, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('删除功能模块成功');
      functionModuleActionRef.current?.reload();
    },
  });
  /**
   * 处理删除功能模块
   * @param record
   */
  const handleDeleteFunctionModule = async (record: API.FunctionModule) => {
    Modal.confirm({
      title: '提示',
      content: '确定要删除该功能模块吗？',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        deleteFunctionModule({ moduleId: record.id });
      },
    });
  };
  const attributeColumns: ProColumns<API.Attribute>[] = [
    {
      title: '属性ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: '属性名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '数据类型',
      dataIndex: 'dataType',
      key: 'dataType',
    },
    {
      title: '附加信息',
      dataIndex: 'addition',
      key: 'addition',
    },
    {
      title: '新增时间',
      dataIndex: 'createTime',
      key: 'createTime',
    },
    {
      title: '描述',
      dataIndex: 'description',
      key: 'description',
    },
    {
      title: '操作',
      valueType: 'option',
      key: 'option',
      render: (_, record) => [
        <a
          key="delete"
          onClick={() => {
            handleDeleteFunctionModule(record);
          }}
        >
          删除
        </a>,
      ],
    },
  ];
  const fetchAttributeData = async (
    params: API.postApiAttributeFilterParams & {
      pageSize?: number;
      current?: number;
    },
  ) => {
    if (!currentModel?.id || !currentModel.productId) {
      return {
        data: [],
        total: 0,
        success: true,
      };
    }
    const { data } = await postApiAttributeFilter(
      {
        pageSize: params.pageSize,
        page: params.current,
      },
      {
        searchFieldModule: 'Attribute',
        searchMatch: 'ALL',
        searchFields: [
          {
            field: 'moduleId',
            operator: 'EqualTo',
            value: currentModel.id,
          },
          {
            field: 'productId',
            operator: 'EqualTo',
            value: currentModel.productId,
          },
        ],
      } as API.SearchFieldQuery,
    );
    return {
      data: data.result || [],
      total: data.totalRows,
      success: true,
    };
  };
  return (
    <ProCard split={responsive ? 'horizontal' : 'vertical'}>
      <ProCard
        colSpan={{
          md: 24,
          xl: 8,
        }}
        ghost
      >
        <ProList<API.FunctionModule>
          tableAlertRender={false}
          actionRef={functionModuleActionRef}
          rowKey={'id'}
          onRow={(record) => {
            return {
              onClick: () => {
                setCurrentModel(record);
              },
            };
          }}
          rowClassName={(record: API.FunctionModule) => {
            const selectedClassName = '!bg-[#f5f5f5] border-r-2 border-primary';
            const defaultClassName = '!px-4 h-16';
            if (record.id === currentModel?.id) {
              return `${selectedClassName} ${defaultClassName}`;
            }
            return defaultClassName;
          }}
          request={fetchFunctionModuleData}
          rowSelection={{
            type: 'radio',
            selectedRowKeys: (currentModel && [currentModel.id]) || [],
            hideSelectAll: true,
            renderCell: () => null,
          }}
          metas={{
            title: {
              dataIndex: 'name',
            },
            description: {
              dataIndex: 'identifier',
              render: (text, record) =>
                record.identifier !== 'default' && <span>标识符：{text}</span>,
            },
            actions: {
              dataIndex: 'actions',
              cardActionProps: 'extra',
              render: (_, record) => [
                record.identifier !== 'default' && (
                  <>
                    <UpdateFunctionModuleForm
                      moduleId={record.id}
                      key="update"
                      reload={() => {
                        functionModuleActionRef.current?.reload();
                      }}
                    ></UpdateFunctionModuleForm>
                    <Button
                      icon={<DeleteOutlined />}
                      key="delete"
                      type={'text'}
                      danger
                      onClick={() => handleDeleteFunctionModule(record)}
                    ></Button>
                  </>
                ),
              ],
            },
          }}
          toolbar={{
            search: {
              placeholder: '请输入功能模块名称',
              onSearch: (value) => {
                console.log(value);
              },
            },
            actions: [
              <CreateFunctionModuleForm
                key="createFunctionModule"
                productId={productId}
                reload={() => {
                  functionModuleActionRef.current?.reload();
                }}
              ></CreateFunctionModuleForm>,
            ],
          }}
        ></ProList>
      </ProCard>
      <ProCard>
        <ProTable<API.Attribute & API.FunctionModule & API.postApiAttributeFilterParams>
          params={currentModel}
          rowKey={'id'}
          search={false}
          columns={attributeColumns}
          request={fetchAttributeData}
          toolbar={{
            actions: [
              <CreateAttributeForm
                initialValues={{
                  productId: currentModel?.productId,
                  moduleId: currentModel?.id,
                }}
                key="createAttribute"
              ></CreateAttributeForm>,
            ],
          }}
        ></ProTable>
      </ProCard>
    </ProCard>
  );
};
const ProductProfile: React.FC = () => {
  const { productId } = useParams();
  const [messageApi, contextHolder] = message.useMessage();
  const { enums, getEnumByValue } = useModel('enumModel');
  /**
   *  获取产品详情
   */
  const {
    data: productInfo,
    loading,
    refresh: productRefresh,
  } = useRequest(() => getApiProductProductId({ productId } as API.getApiProductProductIdParams), {
    refreshDeps: [productId],
  });
  /**
   * 当前页面需要的枚举
   */
  const { ProductStatus } = enums;
  /**
   *  更新产品
   */
  const { run: updateProduct } = useRequest(postApiProductProductId, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('更新产品成功');
      productRefresh();
    },
  });
  /**
   * 删除产品
   */
  const { run: deleteProduct } = useRequest(deleteApiProductProductId, {
    manual: true,
    onSuccess: () => {
      messageApi
        ?.success({
          content: '删除产品成功',
          duration: 1,
        })
        .then(() => {
          history.back();
        });
    },
  });
  /**
   * 发布产品
   */
  const { run: publishProduct } = useRequest(postApiProductProductIdPublish, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('发布产品成功');
      productRefresh();
    },
  });
  const handleMenuClick: MenuProps['onClick'] = (e) => {
    if (e.key === 'delete') {
      Modal.confirm({
        title: '提示',
        content: '确定要删除该产品吗？',
        okText: '确定',
        cancelText: '取消',
        onOk: async () => {
          await deleteProduct({
            productId: productInfo?.id,
          } as API.deleteApiProductProductIdParams);
        },
      });
    } else if (e.key === 'publish') {
      Modal.confirm({
        title: '提示',
        content: '确定要发布该产品吗？',
        okText: '确定',
        cancelText: '取消',
        onOk: async () => {
          await publishProduct({
            productId: productInfo?.id,
          } as API.postApiProductProductIdPublishParams);
        },
      });
    }
  };
  const menuItems: MenuProps['items'] = [
    ...(productInfo && productInfo?.status !== 'Published'
      ? [
          {
            key: 'publish',
            icon: <UploadOutlined />,
            label: '发布',
          },
        ]
      : []),
    {
      key: 'delete',
      icon: <DeleteOutlined />,
      label: '删除',
      danger: true,
    },
  ];
  const [responsive, setResponsive] = useState(false);
  if (loading) {
    return <ProSkeleton type="descriptions" />;
  }
  return (
    <RcResizeObserver
      key="resize-observer"
      onResize={(offset) => {
        setResponsive(offset.width < 596);
      }}
    >
      <PageContainer
        tags={
          productInfo && (
            <Tag color={getEnumByValue(ProductStatus, productInfo.status)?.showStyle.toLowerCase()}>
              {getEnumByValue(ProductStatus, productInfo.status)?.label}
            </Tag>
          )
        }
        tabList={[
          {
            key: 'profile',
            tab: '产品信息',
            children: (
              <ProductBasicInfoForm
                productInfo={productInfo}
                enums={enums}
                submit={async (values) => {
                  await updateProduct(
                    {
                      productId: productInfo?.id,
                    } as API.postApiProductProductIdParams,
                    {
                      ...productInfo,
                      ...values,
                    },
                  );
                }}
              ></ProductBasicInfoForm>
            ),
          },
          {
            key: 'function',
            tab: '功能模块',
            children: (
              <ProductFunctionModuleInfo
                responsive={responsive}
                productId={productId}
                messageApi={messageApi}
              ></ProductFunctionModuleInfo>
            ),
          },
          {
            key: 'device',
            tab: '设备列表',
          },
        ]}
        title={`产品名称：${productInfo?.name || '-'}`}
        content={
          <Paragraph
            copyable={{
              text: `${productInfo?.id}`,
            }}
          >
            ID：{productInfo?.id || '-'}
          </Paragraph>
        }
        extra={
          <Dropdown menu={{ items: menuItems, onClick: handleMenuClick }}>
            <Button type={'primary'}>
              <Space>
                更多
                <DownOutlined />
              </Space>
            </Button>
          </Dropdown>
        }
      >
        {contextHolder}
      </PageContainer>
    </RcResizeObserver>
  );
};
export default ProductProfile;
