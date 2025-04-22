package cn.devicelinks.api.support.search.module;

import cn.devicelinks.component.web.search.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备配置文件检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DeviceProfileSearchFieldModule implements SearchFieldModule {

    SearchField PROFILE_ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PROFILE_NAME = SearchField.of(SearchFieldVariable.DEVICE_PROFILE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PROFILE_FIRMWARE_ID = SearchField.of(SearchFieldVariable.DEVICE_PROFILE_FIRMWARE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PROFILE_SOFTWARE_ID = SearchField.of(SearchFieldVariable.DEVICE_PROFILE_SOFTWARE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.DeviceProfile;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(PROFILE_ID, PROFILE_NAME, PROFILE_FIRMWARE_ID, PROFILE_SOFTWARE_ID);
    }
}
