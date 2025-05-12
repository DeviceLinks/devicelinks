import { postApiUserFilter } from '@/services/device-links-console-ui/user';
import { Modal, Spin, Transfer, TransferProps } from 'antd';
import React, { Key } from 'react';

interface ModalProps {
  open: boolean;
  onCancel: () => void;
  departmentInfo: API.DepartmentTree | undefined;
  refresh: () => void;
}

const BatchAddUser: React.FC<ModalProps> = ({
  open,
  onCancel,
  departmentInfo,
  refresh,
}: ModalProps) => {
  const [allUserList, setAllUserList] = React.useState<API.User[]>([]);
  const [selectedUserIds, setSelectedUserIds] = React.useState<Key[]>([]);
  const [loading, setLoading] = React.useState(false);
  const [current, setCurrent] = React.useState<number>(1);

  const getAllUserList = async () => {
    setLoading(true);
    const result: any = await postApiUserFilter(
      {
        page: current,
        pageSize: 200000,
      },
      {
        searchFieldModule: 'User',
        searchMatch: 'ANY',
        searchFields: [],
      },
    );
    setLoading(false);
    setAllUserList(result.data.result);
  };
  const getSelectedUserIds = async () => {
    setLoading(true);
    const result: any = await postApiUserFilter(
      {
        page: current,
        pageSize: 200000,
      },
      {
        searchFieldModule: 'User',
        searchMatch: 'ANY',
        searchFields: [
          {
            field: 'departmentId',
            operator: 'EqualTo',
            value: departmentInfo?.id ? departmentInfo?.id : '',
          },
        ],
      },
    );
    setLoading(false);
    setSelectedUserIds(result.data.result.map((ele: API.User) => ele.id));
  };
  const onSearch = () => {};
  const onChange: TransferProps['onChange'] = (targetKeys: Key[]) => {
    setSelectedUserIds(targetKeys);
  };
  const handelConfirm = () => {
    onCancel();
    refresh();
  };
  React.useEffect(() => {
    if (open && departmentInfo) {
      getAllUserList();
      getSelectedUserIds();
    }
  }, [open]);
  return (
    <Modal title="添加成员" open={open} onCancel={onCancel} width={800} onOk={handelConfirm}>
      <Spin spinning={loading}>
        <Transfer
          dataSource={allUserList}
          showSearch
          oneWay={true}
          listStyle={{
            width: 850,
            height: 700,
          }}
          titles={['所有成员', '已选中成员']}
          onChange={onChange}
          rowKey={(record: API.User) => record.id || ''}
          onSearch={onSearch}
          targetKeys={selectedUserIds}
          render={(item: API.User) => `${item.name}-${item.phone}`}
        />
      </Spin>
    </Modal>
  );
};
export default BatchAddUser;
