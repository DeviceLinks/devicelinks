import { Button, Flex, message } from 'antd';
import {
  ModalForm,
  ProFormDependency,
  ProFormDigit,
  ProFormGroup,
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
import React from 'react';
import { PlusOutlined } from '@ant-design/icons';
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
  const { data: attributeUnit } = useRequest(getApiAttributeUnit);
  return (
    <>
      {contextHolder}
      <ModalForm<API.AddAttributeRequest>
        title={'新增属性'}
        width="520px"
        modalProps={{ okButtonProps: { loading }, destroyOnClose: true }}
        initialValues={initialValues}
        trigger={
          <Button type="primary" icon={<PlusOutlined />}>
            新增属性
          </Button>
        }
        onFinish={async (values) => {
          await addAttribute(values);
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
                        defaultValue: 10240,
                      }}
                    ></ProFormDigit>
                  );
                case 'ARRAY':
                  return (
                    <>
                      <ProFormRadio.Group
                        name={['info', 'addition', 'elementDataType']}
                        options={AttributeDataType}
                        label={'元素类型'}
                        required
                      ></ProFormRadio.Group>
                      <ProFormDigit
                        label={'元素个数'}
                        required
                        name={['info', 'addition', 'elementCount']}
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
          name={['info', 'addition', 'unitId']}
          label={'单位'}
          allowClear={true}
          showSearch={true}
          placeholder={'请选择单位'}
          options={attributeUnit?.map((item) => ({ label: item.name, value: item.id }))}
        ></ProFormSelect>
        <ProFormTextArea label={'描述'} name={['info', 'description']}></ProFormTextArea>
      </ModalForm>
    </>
  );
};
export default CreateAttributeForm;
