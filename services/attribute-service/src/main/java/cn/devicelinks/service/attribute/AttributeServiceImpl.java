package cn.devicelinks.service.attribute;

import cn.devicelinks.api.support.StatusCodeConstants;
import cn.devicelinks.api.model.converter.AttributeConverter;
import cn.devicelinks.api.model.query.PaginationQuery;
import cn.devicelinks.framework.jdbc.PaginationQueryConverter;
import cn.devicelinks.framework.jdbc.SearchFieldConditionBuilder;
import cn.devicelinks.framework.common.web.search.SearchFieldQuery;
import cn.devicelinks.api.model.request.AddAttributeRequest;
import cn.devicelinks.api.model.request.AttributeInfoRequest;
import cn.devicelinks.api.model.request.UpdateAttributeRequest;
import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.authorization.UserAuthorizedAddition;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.api.model.dto.AttributeDTO;
import cn.devicelinks.framework.jdbc.repositorys.AttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TAttribute.ATTRIBUTE;

/**
 * 属性业务逻辑实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class AttributeServiceImpl extends BaseServiceImpl<Attribute, String, AttributeRepository> implements AttributeService {
    public AttributeServiceImpl(AttributeRepository repository) {
        super(repository);
    }

    @Override
    public AttributeDTO addAttribute(AddAttributeRequest request, UserAuthorizedAddition userAddition) {
        Attribute attribute = this.convertAttribute(request.getInfo(), request.getProductId(), request.getModuleId(), userAddition.getUserId());
        this.repository.insert(attribute);
        // Save child attributes if exist
        List<Attribute> childAttributeList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(request.getChildAttributes())) {
            request.getChildAttributes().forEach(childAttributeInfo -> {
                Attribute childAttribute = this.convertAttribute(childAttributeInfo, request.getProductId(), request.getModuleId(), userAddition.getUserId());
                // Set parent id
                childAttribute.setPid(attribute.getId());
                this.repository.insert(childAttribute);
                childAttributeList.add(childAttribute);
            });
        }
        // Convert to DTO and return
        AttributeDTO attributeDTO = AttributeConverter.INSTANCE.fromEntity(attribute);
        attributeDTO.setChildAttributes(childAttributeList);
        return attributeDTO;
    }

    @Override
    public AttributeDTO updateAttribute(String attributeId, UpdateAttributeRequest request, UserAuthorizedAddition userAddition) {
        Attribute attribute = this.selectById(attributeId);
        if (attribute == null) {
            throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_FOUND, attributeId);
        }
        AttributeInfoRequest attributeInfo = request.getInfo();
        // @formatter:off
        attribute.setName(attributeInfo.getName())
                .setIdentifier(attributeInfo.getIdentifier())
                .setDataType(AttributeDataType.valueOf(attributeInfo.getDataType()))
                .setAddition(attributeInfo.getAddition())
                .setDescription(attributeInfo.getDescription());
        // @formatter:on
        this.update(attribute);
        List<Attribute> savedChildAttributeList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(request.getChildAttributes())) {
            // 删除全部子属性
            this.repository.update(List.of(ATTRIBUTE.DELETED.set(Boolean.TRUE)), ATTRIBUTE.PID.eq(attribute.getId()));
            // 重新添加子属性
            request.getChildAttributes().forEach(childAttributeInfo -> {
                Attribute childAttribute = this.convertAttribute(childAttributeInfo, attribute.getProductId(), attribute.getModuleId(), userAddition.getUserId());
                // Set parent id
                childAttribute.setPid(attribute.getId());
                this.repository.insert(childAttribute);
                savedChildAttributeList.add(childAttribute);
            });
        }
        AttributeDTO attributeDTO = AttributeConverter.INSTANCE.fromEntity(attribute);
        attributeDTO.setChildAttributes(savedChildAttributeList);
        return attributeDTO;
    }

    private Attribute convertAttribute(AttributeInfoRequest attributeInfo, String productId, String moduleId, String createUserId) {
        Attribute attribute = AttributeConverter.INSTANCE.fromAttributeInfo(attributeInfo);
        attribute
                .setProductId(productId)
                .setModuleId(moduleId)
                .setCreateBy(createUserId);
        return attribute;
    }

    @Override
    public PageResult<Attribute> getAttributesByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = SearchFieldConditionBuilder.from(searchFieldQuery).build();
        PaginationQueryConverter converter = PaginationQueryConverter.from(paginationQuery);
        return this.repository.getAttributesByPage(searchFieldConditionList, converter.toPageQuery(), converter.toSortCondition());
    }

    @Override
    public AttributeDTO getAttributeById(String attributeId) {
        Attribute attribute = this.selectById(attributeId);
        if (attribute == null) {
            throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_FOUND, attributeId);
        }
        List<Attribute> childAttributes = this.repository.select(ATTRIBUTE.PID.eq(attribute.getId()), ATTRIBUTE.DELETED.eq(Boolean.FALSE));
        AttributeDTO attributeDTO = AttributeConverter.INSTANCE.fromEntity(attribute);
        if (!ObjectUtils.isEmpty(childAttributes)) {
            attributeDTO.setChildAttributes(childAttributes);
        }
        return attributeDTO;
    }

    @Override
    public Attribute deleteAttribute(String attributeId) {
        Attribute attribute = this.selectById(attributeId);
        if (attribute == null) {
            throw new ApiException(StatusCodeConstants.ATTRIBUTE_NOT_FOUND, attributeId);
        }
        attribute.setDeleted(Boolean.TRUE);
        this.update(attribute);
        return attribute;
    }
}
