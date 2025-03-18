import { FooterToolbar, ProForm, ProFormText } from '@ant-design/pro-components';

interface Props {
  userInfo: API.User;
}

export default (props: Props) => {
  const { userInfo } = props;
  console.log(userInfo);
  return (
    <ProForm
      submitter={{
        render: (_, dom) => <FooterToolbar>{dom}</FooterToolbar>,
      }}
      onFinish={async (values) => console.log(values)}
    >
      <ProForm.Group>
        <ProFormText name="name" label="姓名" tooltip="最长为 24 位" placeholder="请输入姓名" />
        <ProFormText name="company" label="用户名" placeholder="请输入用户名" />
      </ProForm.Group>
    </ProForm>
  );
};
