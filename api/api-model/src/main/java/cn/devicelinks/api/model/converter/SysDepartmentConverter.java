package cn.devicelinks.api.model.converter;

import cn.devicelinks.api.model.response.DepartmentTree;
import cn.devicelinks.entity.SysDepartment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 部门数据转换接口
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface SysDepartmentConverter {
    /**
     * get new mapStruct instance
     */
    SysDepartmentConverter INSTANCE = Mappers.getMapper(SysDepartmentConverter.class);

    DepartmentTree.DepartmentTreeNode fromDepartment(SysDepartment department);

    List<DepartmentTree.DepartmentTreeNode> fromDepartment(List<SysDepartment> departments);
}
