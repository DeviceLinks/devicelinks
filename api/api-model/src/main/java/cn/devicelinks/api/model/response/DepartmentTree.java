package cn.devicelinks.api.model.response;

import cn.devicelinks.api.model.converter.SysDepartmentConverter;
import cn.devicelinks.entity.SysDepartment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门树形结构
 *
 * @author 恒宇少年
 * @since 1.0
 */
public record DepartmentTree(List<DepartmentTreeNode> nodeList) {

    public static DepartmentTreeBuilder with(List<SysDepartment> departmentList) {
        Assert.notEmpty(departmentList, "The department list cannot be empty.");
        return new DepartmentTreeBuilder(departmentList);
    }

    /**
     * The Tree Builder
     */
    public static class DepartmentTreeBuilder {
        private final List<SysDepartment> departmentList;

        public DepartmentTreeBuilder(List<SysDepartment> departmentList) {
            this.departmentList = departmentList;
        }

        public DepartmentTree buildTree() {
            List<DepartmentTreeNode> departments = SysDepartmentConverter.INSTANCE.fromDepartment(departmentList);
            List<DepartmentTreeNode> nodes = new ArrayList<>();
            for (DepartmentTreeNode dept : departments) {
                if (dept.getPid() == null) {
                    nodes.add(dept);
                    fillChildren(dept, departments);
                }
            }
            return new DepartmentTree(nodes);
        }


        public void fillChildren(DepartmentTreeNode parent, List<DepartmentTreeNode> departments) {
            for (DepartmentTreeNode dept : departments) {
                if (dept.getPid() != null && dept.getPid().equals(parent.getId())) {
                    parent.getChildren().add(dept);
                    fillChildren(dept, departments);
                }
            }
        }
    }

    /**
     * The Department Tree Node
     */
    @Getter
    public static class DepartmentTreeNode extends SysDepartment {
        @Setter
        private List<SysDepartment> children = new ArrayList<>();
    }
}
