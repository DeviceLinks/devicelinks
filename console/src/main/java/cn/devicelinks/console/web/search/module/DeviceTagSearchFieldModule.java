package cn.devicelinks.console.web.search.module;

import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备标签检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DeviceTagSearchFieldModule implements SearchFieldModule {
    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.DeviceTag;
    }

    SearchField ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    SearchField NAME = SearchField.of(SearchFieldVariable.DEVICE_TAG_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField DELETED = SearchField.of(SearchFieldVariable.DELETED)
            .setValueType(SearchFieldValueType.BOOLEAN)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));


    @Override
    public List<SearchField> getSearchFields() {
        return List.of(ID, NAME, DELETED);
    }
}
