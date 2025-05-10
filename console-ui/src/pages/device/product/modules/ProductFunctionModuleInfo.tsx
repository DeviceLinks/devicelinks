import React, { useState } from 'react';
import {
  ActionType,
  ProCard,
  ProColumns,
  ProDescriptions,
  ProList,
  ProTable,
} from '@ant-design/pro-components';
import {
  deleteApiFunctionModuleModuleId,
  postApiFunctionModuleFilter,
} from '@/services/device-links-console-ui/functionModule';
import { useRequest } from '@@/exports';
import { Button, message, Modal } from 'antd';
import {
  deleteApiAttributeAttributeId,
  getApiAttributeUnit,
  postApiAttributeFilter,
} from '@/services/device-links-console-ui/attribute';
import UpdateFunctionModuleForm from '@/pages/device/product/modules/UpdateFunctionModuleForm';
import { DeleteOutlined } from '@ant-design/icons';
import CreateFunctionModuleForm from '@/pages/device/product/modules/CreateFunctionModuleForm';
import CreateAttributeForm from '@/pages/device/product/modules/CreateAttributeForm';
import { useModel } from '@umijs/max';
import UpdateAttributeForm from '@/pages/device/product/modules/UpdateAttributeForm';
type ProductFunctionModuleInfoProps = {
  productId: string | undefined;
  responsive: boolean;
};
/**
 * 产品功能模块
 * @param productId
 * @param responsive
 * @constructor
 */
const ProductFunctionModuleInfo: React.FC<ProductFunctionModuleInfoProps> = ({
  productId,
  responsive,
}) => {
  const { enums, getEnumByValue } = useModel('enumModel');
  const { AttributeDataType } = enums;
  const [currentModel, setCurrentModel] = useState<API.FunctionModule>();
  const functionModuleActionRef = React.useRef<ActionType>();
  const attributeActionRef = React.useRef<ActionType>();
  const [messageApi, contextHolder] = message.useMessage();
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
  /** 获取属性单位用于单位回显 */
  const { data: attributeUnit } = useRequest(getApiAttributeUnit);
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
   * 删除属性API
   */
  const { run: deleteAttribute } = useRequest(deleteApiAttributeAttributeId, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('删除属性成功');
      attributeActionRef.current?.reload();
    },
  });
  /**
   * 删除功能模块
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

  /**
   * 删除属性
   * @param record
   */
  const handleDeleteAttribute = async (record: API.Attribute) => {
    Modal.confirm({
      title: '提示',
      content: '确定要删除该属性吗？',
      okText: '确定',
      cancelText: '取消',
      onOk: async () => {
        deleteAttribute({ attributeId: record.id });
      },
    });
  };
  const attributeColumns: ProColumns<API.Attribute>[] = [
    {
      title: '属性ID',
      dataIndex: 'id',
      key: 'id',
      width: 250,
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
      valueType: 'select',
      fieldProps: {
        options: AttributeDataType,
      },
    },
    {
      title: '附加扩展',
      dataIndex: 'addition',
      key: 'addition',
      width: 300,
      render: (_, record) => {
        const { addition, dataType } = record;
        if (!addition) return '-';
        const renderByType = () => {
          switch (dataType) {
            case 'INTEGER':
            case 'DOUBLE':
              return (
                <>
                  {addition.valueRange && (
                    <ProDescriptions.Item
                      label={'取值范围'}
                    >{`${addition.valueRange.min} ~ ${addition.valueRange.max}`}</ProDescriptions.Item>
                  )}
                  <ProDescriptions.Item label={'步长'}>{addition.step}</ProDescriptions.Item>
                </>
              );
            case 'STRING':
              return (
                <>
                  {addition.dataLength && (
                    <ProDescriptions.Item label={'数据长度'}>
                      {addition.dataLength}
                    </ProDescriptions.Item>
                  )}
                </>
              );
            case 'ENUM':
              return (
                <>
                  <ProDescriptions.Item label={''} valueType={'jsonCode'}>
                    {JSON.stringify(addition.valueMap)}
                  </ProDescriptions.Item>
                </>
              );
            case 'JSON':
              return null;
            case 'ARRAY':
              return (
                <>
                  <ProDescriptions.Item label={'元素类型'}>
                    {getEnumByValue(AttributeDataType, addition.elementDataType)?.label}
                  </ProDescriptions.Item>
                  <ProDescriptions.Item label={'元素个数'}>
                    {addition.elementCount}
                  </ProDescriptions.Item>
                </>
              );
            default:
              return null;
          }
        };
        return (
          <ProDescriptions size={'small'} column={1}>
            {renderByType()}
            {addition.unitId && (
              <ProDescriptions.Item label={'单位'}>
                {attributeUnit?.find((item) => item.id === addition.unitId)?.name}
              </ProDescriptions.Item>
            )}
          </ProDescriptions>
        );
      },
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
      align: 'center',
      width: 100,
      render: (_, record) => [
        <UpdateAttributeForm
          key="updateAttribute"
          attributeId={record.id}
          attributeUnit={attributeUnit}
          reload={() => {
            attributeActionRef.current?.reload();
          }}
        ></UpdateAttributeForm>,
        <Button
          type={'link'}
          danger
          key="delete"
          onClick={() => {
            handleDeleteAttribute(record);
          }}
        >
          删除
        </Button>,
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
            field: 'pid',
            operator: 'Is',
            value: null,
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
    <>
      {contextHolder}
      <ProCard split={responsive ? 'horizontal' : 'vertical'}>
        <ProCard ghost colSpan={responsive ? '100%' : '400px'}>
          <ProList<API.FunctionModule>
            tableAlertRender={false}
            className={'custom-right-pro-list'}
            size={'small'}
            key={'id'}
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
              const defaultClassName = '!px-4 h-16 cursor-pointer';
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
            key={'attributeTable'}
            search={false}
            columns={attributeColumns}
            actionRef={attributeActionRef}
            request={fetchAttributeData}
            toolbar={{
              actions: [
                currentModel && (
                  <CreateAttributeForm
                    initialValues={{
                      productId: currentModel?.productId,
                      moduleId: currentModel?.id,
                    }}
                    reload={() => {
                      attributeActionRef.current?.reload();
                    }}
                    key="createAttribute"
                  ></CreateAttributeForm>
                ),
              ],
            }}
          ></ProTable>
        </ProCard>
      </ProCard>
    </>
  );
};

export default ProductFunctionModuleInfo;
