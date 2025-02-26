package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.authorization.UserDetailsContext;
import cn.devicelinks.console.web.request.AddAttributeRequest;
import cn.devicelinks.console.web.StatusCodeConstants;
import cn.devicelinks.console.web.request.AttributeInfoRequest;
import cn.devicelinks.console.web.request.UpdateAttributeRequest;
import cn.devicelinks.console.web.converter.AttributeConverter;
import cn.devicelinks.console.web.query.PaginationQuery;
import cn.devicelinks.console.web.query.SearchFieldQuery;
import cn.devicelinks.console.service.AttributeService;
import cn.devicelinks.framework.common.AttributeDataType;
import cn.devicelinks.framework.common.exception.ApiException;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.common.utils.UUIDUtils;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.model.dto.AttributeDTO;
import cn.devicelinks.framework.jdbc.repositorys.AttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
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
    public AttributeDTO addAttribute(AddAttributeRequest request) {
        Attribute attribute = this.convertAttribute(request.getInfo(), request.getProductId(), request.getModuleId());
        this.repository.insert(attribute);
        // Save child attributes if exist
        List<Attribute> childAttributeList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(request.getChildAttributes())) {
            request.getChildAttributes().forEach(childAttributeInfo -> {
                Attribute childAttribute = this.convertAttribute(childAttributeInfo, request.getProductId(), request.getModuleId());
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
    public AttributeDTO updateAttribute(String attributeId, UpdateAttributeRequest request) {
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
                Attribute childAttribute = this.convertAttribute(childAttributeInfo, attribute.getProductId(), attribute.getModuleId());
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

    private Attribute convertAttribute(AttributeInfoRequest attributeInfo, String productId, String moduleId) {
        String currentUserId = UserDetailsContext.getUserId();
        Attribute attribute = AttributeConverter.INSTANCE.fromAttributeInfo(attributeInfo);
        attribute
                .setId(UUIDUtils.generateNoDelimiter())
                .setProductId(productId)
                .setModuleId(moduleId)
                .setEnabled(Boolean.TRUE)
                .setCreateBy(currentUserId)
                .setCreateTime(LocalDateTime.now());
        return attribute;
    }

    @Override
    public PageResult<Attribute> getAttributesByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = searchFieldQuery.toSearchFieldConditionList();
        return this.repository.getAttributesByPage(searchFieldConditionList, paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
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
