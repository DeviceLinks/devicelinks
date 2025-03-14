import { getApiCommonSearchField } from '@/services/device-links-console-ui/common';
import { CloseOutlined, FilterOutlined } from '@ant-design/icons';
import {
  Button,
  Form,
  FormListFieldData,
  Input,
  Popover,
  Select,
  Space,
  Spin,
  Tooltip,
} from 'antd';
import style from './styles/index.module.css';
import React from 'react';

export const FilterButtonBox = ({
  initialValues,
  confirm,
}: {
  initialValues: API.SearchField;
  confirm: (values: API.SearchField) => void;
}) => {
  const [form] = Form.useForm<API.SearchField>();
  const searchFields = Form.useWatch<API.SearchFieldItem[]>('searchFields', form);
  const [filterOption, setFilterOption] = React.useState<API.SearchFieldModuleItem[]>([]);
  const [loading, setLoading] = React.useState<boolean>(false);
  /**
   * 加载弹框数据
   */
  const fetchApiCommonSearchField = async () => {
    setLoading(true);
    // setFilterOption([]);
    try {
      const { data } = await getApiCommonSearchField({ module: initialValues.searchFieldModule });
      setFilterOption(data);
    } finally {
      setLoading(false);
    }
  };

  React.useEffect(() => {
    fetchApiCommonSearchField();
  }, []);

  const resetFormList = () => {
    form.setFieldValue('searchFields', [{}]);
  };
  const changeFieldType = (rowIndex: number, field: string) => {
    const item: API.SearchFieldItem = {
      field: field,
      operator: undefined,
      value: undefined,
    };
    form.setFieldValue([`searchFields`, rowIndex], item);
  };

  /**
   * 判断最后一个表单项应该显示什么组件
   */
  const getLastFormItem = (formItem: FormListFieldData) => {
    let row = filterOption.find(
      (item) => item.field === (searchFields && searchFields[formItem.name])?.field,
    );
    if (row && row.componentType === 'SELECT') {
      return (
        <Select
          placeholder={'请选择'}
          options={row?.optionStaticData || []}
          style={{ width: '240px' }}
          allowClear
          mode="multiple"
        ></Select>
      );
    } else if (row && row.componentType === 'INPUT') {
      return <Input placeholder={'请输入'} style={{ width: '240px' }}></Input>;
    } else {
      return <Input placeholder={'请输入'} style={{ width: '240px' }}></Input>;
    }
  };

  /**
   * 弹框表单内容
   */
  const content = () => {
    return (
      <>
        <Form
          labelCol={{ span: 6 }}
          wrapperCol={{ span: 18 }}
          form={form}
          style={{ maxWidth: 600, zIndex: 9999, marginTop: '20px' }}
          autoComplete="off"
          initialValues={{
            ...initialValues,
            searchFields: [{}],
          }}
          onFinish={confirm}
        >
          <Form.Item name={'searchFieldModule'} hidden={true}></Form.Item>
          <Form.Item
            name={'searchMatch'}
            className={style.searchMatchBox}
            getValueProps={(val) => ({ value: val })}
          >
            <div className={style.searchMatchSelect}>
              符合以下
              <Select
                variant="filled"
                size={'small'}
                style={{ width: '70px' }}
                placeholder={'请选择'}
                value={initialValues.searchMatch}
                onChange={(val: string) => form.setFieldValue('searchMatch', val)}
                options={[
                  { label: '所有', value: 'ALL' },
                  { label: '任意', value: 'ANY' },
                ]}
              ></Select>
              条件
            </div>
          </Form.Item>
          <Form.List name={'searchFields'}>
            {(fields, { add, remove }) => (
              <div style={{ display: 'flex', flexDirection: 'column', rowGap: 16 }}>
                {fields.map((formItem) => {
                  return (
                    <Space key={formItem.key}>
                      <Form.Item
                        noStyle
                        name={[formItem.name, 'field']}
                        rules={[{ required: true, message: '请选择字段' }]}
                      >
                        <Select
                          placeholder={'请选择字段'}
                          options={filterOption}
                          style={{ width: '140px' }}
                          fieldNames={{
                            label: 'fieldText',
                            value: 'field',
                          }}
                          onChange={(v) => changeFieldType(formItem.name, v)}
                        ></Select>
                      </Form.Item>
                      <Form.Item
                        noStyle
                        name={[formItem.name, 'operator']}
                        rules={[{ required: true, message: '请选择运算符' }]}
                      >
                        <Select
                          placeholder={'请选择运算符'}
                          options={
                            filterOption?.find(
                              (item) =>
                                item.field === (searchFields && searchFields[formItem.name])?.field,
                            )?.operators ?? []
                          }
                          style={{ width: '140px' }}
                          fieldNames={{
                            label: 'description',
                            value: 'value',
                          }}
                        ></Select>
                      </Form.Item>
                      <Form.Item
                        noStyle
                        name={[formItem.name, 'value']}
                        rules={[{ required: true, message: '请选择/输入' }]}
                      >
                        {getLastFormItem(formItem)}
                      </Form.Item>
                      {formItem && fields.length > 1 && (
                        <CloseOutlined
                          onClick={() => {
                            remove(formItem.name);
                          }}
                        />
                      )}
                    </Space>
                  );
                })}
                <div
                  style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
                >
                  <Button type={'dashed'} onClick={() => add()}>
                    + 添加筛选条件
                  </Button>
                  <div>
                    <Button onClick={() => resetFormList()} style={{ marginRight: '10px' }}>
                      重置
                    </Button>
                    <Button type={'primary'} htmlType={'submit'}>
                      搜索
                    </Button>
                  </div>
                </div>
              </div>
            )}
          </Form.List>
        </Form>
      </>
    );
  };
  return (
    <Popover title={'设置筛选条件'} content={content} placement="bottom" trigger={'click'}>
      <Tooltip title={'筛选'}>
        {loading ? (
          <Spin size={'small'} percent={'auto'}></Spin>
        ) : (
          <FilterOutlined className={style.filterIcon} />
        )}
      </Tooltip>
    </Popover>
  );
};
