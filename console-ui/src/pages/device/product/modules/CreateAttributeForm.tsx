import { Button, Flex, Form, message, Space } from 'antd';
import {
  ModalForm,
  ProFormDependency,
  ProFormDigit,
  ProFormGroup,
  ProFormInstance,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useModel, useRequest } from '@umijs/max';
import {
  getApiAttributeUnit,
  postApiAttribute,
} from '@/services/device-links-console-ui/attribute';
import React, { useRef } from 'react';
import { PlusOutlined } from '@ant-design/icons';
type WriteChildAttributeModalFormProps = {
  trigger?: JSX.Element;
  submitter?: (formData: API.AttributeInfoRequest) => Promise<void>;
  initialValues?: API.AttributeInfoRequest;
};
const WriteChildAttributeModalForm: React.FC<WriteChildAttributeModalFormProps> = ({
  trigger,
  submitter,
  initialValues,
}) => {
  const { enums } = useModel('enumModel');
  const { AttributeDataType } = enums;
  const { data: attributeUnit } = useRequest(getApiAttributeUnit);
  return (
    <ModalForm<API.AttributeInfoRequest>
      trigger={trigger}
      title={'新增子属性'}
      width="520px"
      modalProps={{
        destroyOnClose: true,
      }}
      onFinish={async (values) => {
        submitter?.(values);
        return true;
      }}
      initialValues={{
        writable: true,
        addition: {
          dataLength: 10240,
        },
        ...initialValues,
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
                        ></ProFormDigit>
                        <div>~</div>
                        <ProFormDigit
                          name={['addition', 'valueRange', 'max']}
                          placeholder={'最大值'}
                        ></ProFormDigit>
                      </Flex>
                    </ProFormGroup>
                    <ProFormDigit label={'步长'} name={['addition', 'step']}></ProFormDigit>
                  </>
                );
              case 'STRING':
                return (
                  <ProFormDigit
                    label={'数据长度'}
                    name={['addition', 'dataLength']}
                    required
                    placeholder={'请输入数据长度'}
                    fieldProps={{
                      suffix: '字节',
                    }}
                  ></ProFormDigit>
                );
              case 'ARRAY':
                return (
                  <>
                    <ProFormRadio.Group
                      name={['addition', 'elementDataType']}
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
                      name={['addition', 'elementCount']}
                    ></ProFormDigit>
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

type CreateAttributeFormProps = {
  reload?: () => void;
  initialValues?: {
    productId?: string;
    moduleId?: string;
  };
};
const CreateAttributeForm: React.FC<CreateAttributeFormProps> = ({ reload, initialValues }) => {
  const { enums } = useModel('enumModel');
  const { AttributeDataType } = enums;
  const [messageApi, contextHolder] = message.useMessage();
  const { run: addAttribute, loading } = useRequest(postApiAttribute, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('新增属性成功');
      reload?.();
    },
  });
  const formRef = useRef<ProFormInstance>();
  const { data: attributeUnit } = useRequest(getApiAttributeUnit);
  return (
    <>
      {contextHolder}
      <ModalForm<API.AddAttributeRequest>
        title={'新增属性'}
        formRef={formRef}
        width="520px"
        modalProps={{ okButtonProps: { loading }, destroyOnClose: true }}
        initialValues={{
          ...initialValues,
          info: {
            writable: true,
            addition: {
              dataLength: 10240,
            },
          },
        }}
        trigger={
          <Button type="primary" icon={<PlusOutlined />}>
            新增属性
          </Button>
        }
        onFinish={async (values) => {
          console.log(values);
          // await addAttribute(values);
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
        <ProFormDependency name={[['info', 'dataType']]}>
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
                        <Flex gap={'middle'}>
                          <ProFormDigit
                            name={['info', 'addition', 'valueRange', 'min']}
                            placeholder={'最小值'}
                          ></ProFormDigit>
                          <div>~</div>
                          <ProFormDigit
                            name={['info', 'addition', 'valueRange', 'max']}
                            placeholder={'最大值'}
                          ></ProFormDigit>
                        </Flex>
                      </ProFormGroup>
                      <ProFormDigit
                        label={'步长'}
                        name={['info', 'addition', 'step']}
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
                      fieldProps={{
                        suffix: '字节',
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
                      ></ProFormDigit>
                    </>
                  );
                case 'ENUM':
                  return (
                    <ProFormGroup
                      titleStyle={{ marginBottom: 8, fontWeight: 'normal' }}
                      label={'枚举项'}
                      align={'center'}
                    >
                      {/* <FormList name={['info', 'addition', 'valueMap']}>
                        {(fields, { add, remove }) => {})
                      </FormList>*/}
                    </ProFormGroup>
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
                                  ></WriteChildAttributeModalForm>
                                  <Button
                                    type={'link'}
                                    size={'small'}
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
                            trigger={
                              <Button type={'link'} icon={<PlusOutlined />}>
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
export default CreateAttributeForm;
