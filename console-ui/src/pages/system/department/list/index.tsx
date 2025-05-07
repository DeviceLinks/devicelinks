import CreateDepartment from '@/pages/system/department/modules/createDepartment';
import AddUser from '@/pages/system/user/modules/CreateUserForm';
import { postApiDepartmentTreeFilter } from '@/services/device-links-console-ui/department';
import {
  DeleteOutlined,
  EditOutlined,
  EllipsisOutlined,
  PlusOutlined,
  PullRequestOutlined,
} from '@ant-design/icons';
import { PageContainer } from '@ant-design/pro-components';
import { Button, Menu, Popover, Spin, Tree } from 'antd';
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
                field: 'name',
                operator: 'Like',
                value: inputValue,
              },
            ]
          : [],
      });
      setTreeData(data);
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

  const handleClickMenu = ({ key }: any) => {
    console.log(key);
    if (key === 'add') {
      setEditDepartmentInfo(undefined);
      setOpenAddModal(true);
    } else if (key === 'edit') {
      setEditDepartmentInfo(selectedNode);
      setOpenAddModal(true);
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
      extra={[<AddUser key={'department'} refresh={refreshData} />]}
    >
      <div className={style.content}>
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
              titleRender={treeTitleRender}
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

        <div className={style.userList}></div>
      </div>
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
