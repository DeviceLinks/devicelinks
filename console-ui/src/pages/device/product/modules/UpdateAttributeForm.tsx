import { Button, Flex, Form, message, Space } from 'antd';
import {
  ModalForm,
  ProFormDependency,
  ProFormDigit,
  ProFormGroup,
  ProFormInstance,
  ProFormList,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useModel, useRequest } from '@umijs/max';
import {
  getApiAttributeAttributeId,
  postApiAttributeAttributeId,
} from '@/services/device-links-console-ui/attribute';
import React, { useRef } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { cloneDeep } from 'lodash';
type WriteChildAttributeModalFormProps = {
  trigger?: JSX.Element;
  submitter?: (formData: API.AttributeInfoRequest) => Promise<void>;
  initialValues?: API.AttributeInfoRequest;
  attributeUnit?: API.AttributeUnit[];
};
const WriteChildAttributeModalForm: React.FC<WriteChildAttributeModalFormProps> = ({
  trigger,
  submitter,
  initialValues,
  attributeUnit,
}) => {
  const { enums } = useModel('enumModel');
  const { AttributeDataType } = enums;
  const formRef = useRef<ProFormInstance>();
  //antPro存在bug ProFormList 在第一次提交时没有触发transform
  const firstSubmit = useRef(true);
  const fetchData = async () => {
    if (!initialValues) return {} as API.AttributeInfoRequest;
    const data = cloneDeep(initialValues);
    const { dataType, addition } = data;
    if (dataType === 'ENUM' && addition?.valueMap) {
      const valueMap: Record<string, string> = addition.valueMap as Record<string, string>;
      addition.valueMap = Object.keys(valueMap).map((key) => {
        return {
          label: valueMap[key],
          value: key,
        };
      });
    }
    console.log(data, 'data');
    return data;
  };
  return (
    <ModalForm<API.AttributeInfoRequest>
      trigger={trigger}
      title={'新增子属性'}
      width="520px"
      formRef={formRef}
      modalProps={{
        destroyOnClose: true,
      }}
      request={fetchData}
      onFinish={async (values) => {
        if (firstSubmit.current) {
          firstSubmit.current = false;
          formRef.current?.submit();
          return false;
        }
        firstSubmit.current = true;
        submitter?.(values);
        return true;
      }}
    >
      <ProFormText
        label={'名称'}
        name={'name'}
        placeholder={'请输入子属性名称'}
        required
        rules={[
          {
            required: true,
            message: '请输入子属性名称',
          },
          {
            pattern: /^[\u4e00-\u9fa5a-zA-Z0-9][\u4e00-\u9fa5a-zA-Z0-9_-]*$/,
            message: '必须以汉字、字母或数字开头，后续可包含汉字、字母、数字、_ 或 -',
          },
        ]}
      ></ProFormText>
      <ProFormText
        label={'标识符'}
        name={'identifier'}
        placeholder={'请输入子属性标识符'}
        required
        rules={[
          {
            required: true,
            message: '请输入子属性标识符',
          },
          {
            pattern: /^[a-zA-Z][a-zA-Z0-9_]*$/,
            message: '必须以字母开头，后续可包含字母、数字或下划线',
          },
        ]}
      ></ProFormText>
      <ProFormSelect
        label={'数据类型'}
        placeholder={'请选择数据类型'}
        name={'dataType'}
        options={AttributeDataType.filter(
          (item) => item.value !== 'ARRAY' && item.value !== 'JSON',
        )}
        required
      ></ProFormSelect>
      <ProFormDependency name={['dataType']}>
        {({ dataType }) => {
          const renderFields = () => {
            switch (dataType) {
              case 'INTEGER':
              case 'DOUBLE':
                return (
                  <>
                    <ProFormGroup
                      titleStyle={{ marginBottom: 8, fontWeight: 'normal' }}
                      label={'取值范围'}
                      align={'center'}
                    >
                      <Flex gap={'middle'}>
                        <ProFormDigit
                          name={['addition', 'valueRange', 'min']}
                          placeholder={'最小值'}
                          fieldProps={dataType === 'INTEGER' ? { precision: 0 } : {}}
                          rules={[
                            {
                              validator: (_: any, value: number) => {
                                const max = formRef.current?.getFieldValue([
                                  'addition',
                                  'valueRange',
                                  'max',
                                ]);
                                if (value && max && value > max) {
                                  return Promise.reject('最小值不能大于最大值');
                                } else {
                                  return Promise.resolve();
                                }
                              },
                            },
                          ]}
                        ></ProFormDigit>
                        <div>~</div>
                        <ProFormDigit
                          name={['addition', 'valueRange', 'max']}
                          placeholder={'最大值'}
                          fieldProps={dataType === 'INTEGER' ? { precision: 0 } : {}}
                          rules={[
                            {
                              validator: (_: any, value: number) => {
                                const min = formRef.current?.getFieldValue([
                                  'addition',
                                  'valueRange',
                                  'min',
                                ]);
                                if (value && min && value < min) {
                                  return Promise.reject('最大值不能小于最小值');
                                } else {
                                  return Promise.resolve();
                                }
                              },
                            },
                          ]}
                        ></ProFormDigit>
                      </Flex>
                    </ProFormGroup>
                    <ProFormDigit
                      label={'步长'}
                      name={['addition', 'step']}
                      fieldProps={dataType === 'INTEGER' ? { precision: 0 } : {}}
                    ></ProFormDigit>
                  </>
                );
              case 'STRING':
                return (
                  <ProFormDigit
                    label={'数据长度'}
                    name={['addition', 'dataLength']}
                    required
                    initialValue={10240}
                    placeholder={'请输入数据长度'}
                    fieldProps={{
                      suffix: '字节',
                      precision: 0,
                    }}
                  ></ProFormDigit>
                );
              case 'ENUM':
                return (
                  <>
                    <ProFormList
                      min={1}
                      copyIconProps={false}
                      label={'枚举项'}
                      name={['addition', 'valueMap']}
                      transform={(
                        value: Array<{
                          value: string;
                          label: string;
                        }>,
                        namePath: string,
                      ) => {
                        const res: Record<string, string> = {};
                        value?.forEach((item: any) => {
                          res[item.value] = item.label;
                        });
                        return {
                          addition: {
                            [namePath]: res,
                          },
                        };
                      }}
                      creatorButtonProps={{
                        creatorButtonText: '添加枚举项',
                      }}
                      creatorRecord={{
                        value: '',
                        label: '',
                      }}
                    >
                      <Space>
                        <ProFormText
                          name={'value'}
                          placeholder={'请输入枚举值'}
                          required
                          rules={[
                            {
                              required: true,
                              message: '请输入枚举值',
                            },
                          ]}
                        ></ProFormText>
                        <ProFormText
                          name={'label'}
                          placeholder={'请输入枚举描述'}
                          required
                          rules={[
                            {
                              required: true,
                              message: '请输入枚举描述',
                            },
                          ]}
                        ></ProFormText>
                      </Space>
                    </ProFormList>
                  </>
                );
              default:
                return null;
            }
          };
          return <>{renderFields()}</>;
        }}
      </ProFormDependency>
      <ProFormSelect
        name={['addition', 'unitId']}
        label={'单位'}
        allowClear={true}
        showSearch={true}
        placeholder={'请选择单位'}
        options={attributeUnit?.map((item) => ({ label: item.name, value: item.id }))}
      ></ProFormSelect>
      <ProFormRadio.Group
        label={'读写类型'}
        name={['writable']}
        fieldProps={{
          size: 'small',
          defaultValue: true,
        }}
        options={[
          {
            label: '读写',
            value: true,
          },
          {
            label: '只读',
            value: false,
          },
        ]}
        required
      ></ProFormRadio.Group>
      <ProFormTextArea label={'描述'} name={['description']}></ProFormTextArea>
    </ModalForm>
  );
};
type UpdateAttributeFormProps = {
  reload?: () => void;
  attributeId: string;
  attributeUnit?: API.AttributeUnit[];
};
const UpdateAttributeForm: React.FC<UpdateAttributeFormProps> = ({
  reload,
  attributeId,
  attributeUnit,
}) => {
  const { enums } = useModel('enumModel');
  const { AttributeDataType } = enums;
  const [messageApi, contextHolder] = message.useMessage();
  const { run: updateAttribute, loading } = useRequest(postApiAttributeAttributeId, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('更新属性成功');
      reload?.();
    },
  });
  const formRef = useRef<ProFormInstance>();
  //antPro存在bug ProFormList 在第一次提交时没有触发transform
  const firstSubmit = useRef(true);
  //获取属性详情
  const fetchData = async () => {
    const { data } = await getApiAttributeAttributeId({
      attributeId,
    });
    if (data?.dataType === 'ENUM' && data?.addition?.valueMap) {
      const valueMap: Record<string, string> = data.addition.valueMap as Record<string, string>;
      data.addition.valueMap = Object.keys(valueMap).map((key) => {
        return {
          label: valueMap[key],
          value: key,
        };
      });
    }
    return {
      info: {
        ...data,
      },
      childAttributes: data?.childAttributes || [],
    } as API.UpdateAttributeRequest;
  };
  return (
    <>
      {contextHolder}
      <ModalForm<API.UpdateAttributeRequest>
        title={'编辑属性'}
        formRef={formRef}
        request={fetchData}
        width="520px"
        modalProps={{ okButtonProps: { loading }, destroyOnClose: true }}
        trigger={
          <Button type={'link'} key="edit">
            编辑
          </Button>
        }
        onFinish={async (values) => {
          if (firstSubmit.current) {
            firstSubmit.current = false;
            formRef.current?.submit();
            return false;
          }
          firstSubmit.current = true;
          await updateAttribute(
            {
              attributeId,
            },
            values,
          );
          return true;
        }}
      >
        <ProFormText name="productId" hidden />
        <ProFormText name="moduleId" hidden />
        <ProFormText
          label={'名称'}
          name={['info', 'name']}
          placeholder={'请输入属性名称'}
          required
          rules={[
            {
              required: true,
              message: '请输入属性名称',
            },
            {
              pattern: /^[\u4e00-\u9fa5a-zA-Z0-9][\u4e00-\u9fa5a-zA-Z0-9_-]*$/,
              message: '必须以汉字、字母或数字开头，后续可包含汉字、字母、数字、_ 或 -',
            },
          ]}
        ></ProFormText>
        <ProFormText
          label={'标识符'}
          name={['info', 'identifier']}
          placeholder={'请输入属性标识符'}
          required
          rules={[
            {
              required: true,
              message: '请输入属性标识符',
            },
            {
              pattern: /^[a-zA-Z][a-zA-Z0-9_]*$/,
              message: '必须以字母开头，后续可包含字母、数字或下划线',
            },
          ]}
        ></ProFormText>
        <ProFormSelect
          label={'数据类型'}
          placeholder={'请选择数据类型'}
          name={['info', 'dataType']}
          options={AttributeDataType}
          required
        ></ProFormSelect>
        <ProFormDependency
          name={[
            ['info', 'dataType'],
            ['info', 'addition', 'valueMap'],
          ]}
        >
          {({ info }) => {
            const dataType = info?.dataType;
            const renderFields = () => {
              switch (dataType) {
                case 'INTEGER':
                case 'DOUBLE':
                  return (
                    <>
                      <ProFormGroup
                        titleStyle={{ marginBottom: 8, fontWeight: 'normal' }}
                        label={'取值范围'}
                        align={'center'}
                      >
                        {/* 官方提供的 ProFormDigitRange 存在bug*/}
                        <Flex gap={'middle'}>
                          <ProFormDigit
                            name={['info', 'addition', 'valueRange', 'min']}
                            placeholder={'最小值'}
                            fieldProps={dataType === 'INTEGER' ? { precision: 0 } : {}}
                            rules={[
                              {
                                validator: (_: any, value: number) => {
                                  const max = formRef.current?.getFieldValue([
                                    'info',
                                    'addition',
                                    'valueRange',
                                    'max',
                                  ]);
                                  if (value && max && value > max) {
                                    return Promise.reject(new Error('最小值必须小于最大值'));
                                  } else {
                                    return Promise.resolve();
                                  }
                                },
                              },
                            ]}
                          ></ProFormDigit>
                          <div>~</div>
                          <ProFormDigit
                            name={['info', 'addition', 'valueRange', 'max']}
                            placeholder={'最大值'}
                            fieldProps={dataType === 'INTEGER' ? { precision: 0 } : {}}
                            rules={[
                              {
                                validator: (_: any, value: number) => {
                                  const min = formRef.current?.getFieldValue([
                                    'info',
                                    'addition',
                                    'valueRange',
                                    'min',
                                  ]);
                                  if (value && min && value < min) {
                                    return Promise.reject(new Error('最大值必须大于最小值'));
                                  } else {
                                    return Promise.resolve();
                                  }
                                },
                              },
                            ]}
                          ></ProFormDigit>
                        </Flex>
                      </ProFormGroup>
                      <ProFormDigit
                        label={'步长'}
                        name={['info', 'addition', 'step']}
                        placeholder={'请输入步长'}
                        fieldProps={dataType === 'INTEGER' ? { precision: 0 } : {}}
                      ></ProFormDigit>
                    </>
                  );
                case 'STRING':
                  return (
                    <ProFormDigit
                      label={'数据长度'}
                      name={['info', 'addition', 'dataLength']}
                      required
                      placeholder={'请输入数据长度'}
                      initialValue={10240}
                      fieldProps={{
                        suffix: '字节',
                        precision: 0,
                      }}
                    ></ProFormDigit>
                  );
                case 'ARRAY':
                  return (
                    <>
                      <ProFormRadio.Group
                        name={['info', 'addition', 'elementDataType']}
                        options={[
                          {
                            label: '整型',
                            value: 'INTEGER',
                          },
                          {
                            label: '浮点型',
                            value: 'DOUBLE',
                          },
                          {
                            label: '字符串',
                            value: 'STRING',
                          },
                          {
                            label: 'JSON',
                            value: 'JSON',
                          },
                        ]}
                        label={'元素类型'}
                        required
                        rules={[
                          {
                            required: true,
                            message: '请选择元素类型',
                          },
                        ]}
                      ></ProFormRadio.Group>
                      <ProFormDigit
                        label={'元素个数'}
                        required
                        name={['info', 'addition', 'elementCount']}
                        fieldProps={{
                          precision: 0,
                        }}
                      ></ProFormDigit>
                    </>
                  );
                case 'ENUM':
                  return (
                    <>
                      {/* <ProFormItem
                        required
                        label={'枚举项'}
                        name={['info', 'addition', 'valueMap']}
                        convertValue={(value?: Record<string, string>) => {
                          if (!value) return [];
                          return Object.keys(value).map((key) => {
                            return {
                              label: value[key],
                              value: key,
                            };
                          });
                        }}
                      ></ProFormItem>*/}
                      {/* ProFormList 不支持 convertValue  数据回显 需要在convertValue之后*/}
                      <ProFormList
                        label={'枚举项'}
                        name={['info', 'addition', 'valueMap']}
                        required
                        copyIconProps={false}
                        transform={(
                          value: Array<{
                            value: string;
                            label: string;
                          }>,
                        ) => {
                          const res: Record<string, string> = {};
                          value?.forEach((item: any) => {
                            res[item.value] = item.label;
                          });
                          return {
                            info: {
                              addition: {
                                valueMap: res,
                              },
                            },
                          };
                        }}
                        min={1}
                        creatorButtonProps={{
                          creatorButtonText: '添加枚举项',
                        }}
                        creatorRecord={{
                          value: '',
                          label: '',
                        }}
                      >
                        <Space>
                          <ProFormText
                            name={'value'}
                            placeholder={'请输入枚举值'}
                            required
                            rules={[
                              {
                                required: true,
                                message: '请输入枚举值',
                              },
                            ]}
                          ></ProFormText>
                          <ProFormText
                            name={'label'}
                            placeholder={'请输入枚举描述'}
                            required
                            rules={[
                              {
                                required: true,
                                message: '请输入枚举描述',
                              },
                            ]}
                          ></ProFormText>
                        </Space>
                      </ProFormList>
                    </>
                  );
                default:
                  return null;
              }
            };
            return <>{renderFields()}</>;
          }}
        </ProFormDependency>
        <ProFormDependency
          name={[['info', 'dataType'], ['info', 'addition', 'elementDataType'], 'childAttributes']}
        >
          {({ info, childAttributes }) => {
            const dataType = info?.dataType;
            const elementDataType = info?.addition?.elementDataType;
            if ((dataType === 'ARRAY' && elementDataType === 'JSON') || dataType === 'JSON') {
              return (
                <Form.Item label={'JSON 对象'} required>
                  <Form.List name={'childAttributes'}>
                    {(fields, { add, remove }) => {
                      return (
                        <>
                          {fields.map((field) => {
                            return (
                              <Flex
                                key={field.key}
                                justify={'space-between'}
                                align={'center'}
                                rootClassName={'bg-[#E3F1FB] mb-2 p-2 box-border'}
                              >
                                <span className={'text-gray-500'}>
                                  属性名称：
                                  {childAttributes[field.name]?.name}
                                </span>
                                <Space align={'center'}>
                                  <WriteChildAttributeModalForm
                                    initialValues={childAttributes[field.name]}
                                    trigger={
                                      <Button type={'link'} size={'small'}>
                                        编辑
                                      </Button>
                                    }
                                    attributeUnit={attributeUnit}
                                    submitter={async (values) => {
                                      formRef.current?.setFieldsValue({
                                        childAttributes: {
                                          [field.name]: values,
                                        },
                                      });
                                    }}
                                  ></WriteChildAttributeModalForm>
                                  <Button
                                    type={'link'}
                                    size={'small'}
                                    danger
                                    onClick={() => {
                                      remove(field.name);
                                    }}
                                  >
                                    删除
                                  </Button>
                                </Space>
                              </Flex>
                            );
                          })}
                          <WriteChildAttributeModalForm
                            key={'addChildAttribute'}
                            attributeUnit={attributeUnit}
                            trigger={
                              <Button
                                icon={<PlusOutlined />}
                                color="default"
                                variant="dashed"
                                className={'w-full'}
                              >
                                新增子属性
                              </Button>
                            }
                            submitter={async (values) => {
                              add(values);
                            }}
                          ></WriteChildAttributeModalForm>
                        </>
                      );
                    }}
                  </Form.List>
                </Form.Item>
              );
            }
            return null;
          }}
        </ProFormDependency>
        <ProFormSelect
          name={['info', 'addition', 'unitId']}
          label={'单位'}
          allowClear={true}
          showSearch={true}
          placeholder={'请选择单位'}
          options={attributeUnit?.map((item) => ({ label: item.name, value: item.id }))}
        ></ProFormSelect>
        <ProFormRadio.Group
          label={'读写类型'}
          name={['info', 'writable']}
          fieldProps={{
            size: 'small',
            defaultValue: true,
          }}
          options={[
            {
              label: '读写',
              value: true,
            },
            {
              label: '只读',
              value: false,
            },
          ]}
          required
        ></ProFormRadio.Group>
        <ProFormTextArea label={'描述'} name={['info', 'description']}></ProFormTextArea>
      </ModalForm>
    </>
  );
};
export default UpdateAttributeForm;
