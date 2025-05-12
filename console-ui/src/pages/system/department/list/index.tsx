import CreateDepartment from '@/pages/system/department/modules/createDepartment';
import UserContent from '@/pages/system/department/modules/userContent';
import AddUser from '@/pages/system/user/modules/CreateUserForm';
import {
  deleteApiDepartmentDepartmentId,
  postApiDepartmentTreeFilter,
} from '@/services/device-links-console-ui/department';
import {
  DeleteOutlined,
  DownOutlined,
  EditOutlined,
  EllipsisOutlined,
  ImportOutlined,
  PlusOutlined,
  PullRequestOutlined,
} from '@ant-design/icons';
import { PageContainer, ProCard } from '@ant-design/pro-components';
import { Button, Menu, message, Modal, Popover, Spin, Tree } from 'antd';
import Search from 'antd/es/input/Search';
import React from 'react';
import style from '../styles/index.module.css';

const DepartmentList: React.FC = () => {
  const refreshData = () => {};
  const [treeData, setTreeData] = React.useState<API.DepartmentTree[]>([]);
  const [treeLoading, setTreeLoading] = React.useState(true);
  const treeFieldNames = { title: 'name', key: 'id', children: 'children' };
  const [selectedNode, setSelectedNode] = React.useState<API.DepartmentTree>();
  const [openAddModal, setOpenAddModal] = React.useState(false);
  const [editDepartmentInfo, setEditDepartmentInfo] = React.useState<API.DepartmentTree>();

  const treeMoreBtnList = [
    {
      key: 'add',
      label: '添加子部门',
      icon: <PlusOutlined />,
    },
    {
      key: 'edit',
      label: '编辑部门',
      icon: <EditOutlined />,
    },
    {
      key: 'move',
      label: '移动部门',
      icon: <PullRequestOutlined />,
    },
    {
      key: 'delete',
      label: '删除部门',
      icon: <DeleteOutlined />,
      danger: true,
      popupClassName: style.btnItem,
    },
  ];
  /**
   * 获取组织结构树
   * @param inputValue
   */
  const getTreeData = async (inputValue?: string | null) => {
    setTreeLoading(true);
    try {
      const { data } = await postApiDepartmentTreeFilter({
        searchFieldModule: 'Department',
        searchMatch: 'ALL',
        searchFields: inputValue
          ? [
              {
                field: 'deleted',
                operator: 'EqualTo',
                value: 'false',
              },
              {
                field: 'name',
                operator: 'Like',
                value: inputValue,
              },
            ]
          : [
              {
                field: 'deleted',
                operator: 'EqualTo',
                value: false,
              },
            ],
      });
      setTreeData(data);
      if (data && data.length > 0) {
        setSelectedNode(data?.[0]);
      }
    } finally {
      setTreeLoading(false);
    }
  };

  /**
   * 输入框筛选
   * @param value
   */
  const filterTreeData = (value: string | null) => {
    getTreeData(value);
  };

  /**
   * 打开新增/编辑弹框
   */
  const openAddDepartmentModal = () => {
    setEditDepartmentInfo(undefined);
    setOpenAddModal(true);
  };

  const deleteDepartmentFunc = () => {
    Modal.confirm({
      title: '提示',
      content: '删除部门后不可恢复，确认删除该部门？',
      onOk: async () => {
        await deleteApiDepartmentDepartmentId({
          departmentId: selectedNode?.id,
        } as API.deleteApiDepartmentDepartmentIdParams);
        message.success('删除成功');
        getTreeData();
      },
    });
  };

  const handleClickMenu = ({ key }: any) => {
    console.log(key);
    if (key === 'add') {
      setEditDepartmentInfo(undefined);
      setOpenAddModal(true);
    } else if (key === 'edit') {
      setEditDepartmentInfo(selectedNode);
      setOpenAddModal(true);
    } else if (key === 'move') {
    } else if (key === 'delete') {
      deleteDepartmentFunc();
    }
  };

  /**
   * 按钮（右键）
   */
  const moreBtn = () => {
    return (
      <Menu
        style={{ width: 145 }}
        mode="inline"
        items={treeMoreBtnList}
        onClick={handleClickMenu}
      />
    );
  };

  /**
   * 子节点渲染
   * @param data
   */
  const treeTitleRender = (data: API.DepartmentTree) => {
    return (
      <div className={style.departmentTreeNode}>
        <span>{data.name}</span>
        {selectedNode?.id === data.id && (
          <Popover
            content={moreBtn}
            rootClassName={'btn-popover'}
            placement={'bottom'}
            styles={{ body: { padding: '0px' } }}
          >
            <EllipsisOutlined className={style.treeNodeIcon} />
          </Popover>
        )}
      </div>
    );
  };

  const handleSelectNode = (_keys: any[], { selectedNodes }: any) => {
    setSelectedNode(selectedNodes?.[0]);
  };

  React.useEffect(() => {
    getTreeData();
  }, []);

  return (
    <PageContainer
      title={'部门管理'}
      content={'维护组织内多层级部门，并管理成员的部门隶属关系。'}
      extra={[
        <AddUser key={'department'} refresh={refreshData} />,
        <Button
          type={'primary'}
          icon={<ImportOutlined />}
          key={'import'}
          onClick={() => message.warning('该功能正在开发中...')}
        >
          导入组织机构
        </Button>,
      ]}
    >
      <ProCard split="vertical">
        <ProCard colSpan="20%">
          <Spin spinning={treeLoading}>
            <div className={style.tree}>
              <Search
                style={{ marginBottom: 8 }}
                placeholder="请输入部门名称搜索"
                onSearch={filterTreeData}
              />
              <Tree
                treeData={treeData}
                defaultExpandAll={true}
                fieldNames={treeFieldNames}
                blockNode
                showLine
                switcherIcon={<DownOutlined />}
                titleRender={treeTitleRender}
                selectedKeys={[selectedNode?.id ?? '']}
                onSelect={handleSelectNode}
              />
              <Button
                type={'primary'}
                ghost={true}
                icon={<PlusOutlined />}
                className={style.addDepartmentBtn}
                onClick={openAddDepartmentModal}
              >
                新建部门
              </Button>
            </div>
          </Spin>
        </ProCard>
        <UserContent department={selectedNode} />
      </ProCard>
      <CreateDepartment
        open={openAddModal}
        onCancel={() => setOpenAddModal(false)}
        departmentInfo={editDepartmentInfo}
        refresh={() => getTreeData()}
      ></CreateDepartment>
    </PageContainer>
  );
};
export default DepartmentList;
