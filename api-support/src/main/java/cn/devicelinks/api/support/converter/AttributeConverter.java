package cn.devicelinks.api.support.converter;

import cn.devicelinks.api.support.request.AttributeInfoRequest;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 属性转换器
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Mapper
public interface AttributeConverter {
    /**
     * get new mapStruct instance
     */
    AttributeConverter INSTANCE = Mappers.getMapper(AttributeConverter.class);

    /**
     * 将{@link Attribute}转换为{@link AttributeDTO}
     *
     * @param attribute 属性对象实例 @link Attribute}
     * @return {@link AttributeDTO}
     */
    AttributeDTO fromEntity(Attribute attribute);

    @Mappings(
            @Mapping(target = "dataType", expression = "java(cn.devicelinks.framework.common.AttributeDataType.valueOf(attributeInfo.getDataType()))")
    )
    Attribute fromAttributeInfo(AttributeInfoRequest attributeInfo);
}
