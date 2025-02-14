package cn.devicelinks.console.service.impl;

import cn.devicelinks.console.model.page.PaginationQuery;
import cn.devicelinks.console.model.search.SearchFieldQuery;
import cn.devicelinks.console.service.AttributeService;
import cn.devicelinks.framework.common.pojos.Attribute;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.core.page.PageResult;
import cn.devicelinks.framework.jdbc.core.sql.SearchFieldCondition;
import cn.devicelinks.framework.jdbc.repositorys.AttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PageResult<Attribute> getAttributesByPage(PaginationQuery paginationQuery, SearchFieldQuery searchFieldQuery) {
        List<SearchFieldCondition> searchFieldConditionList = searchFieldQuery.toSearchFieldConditionList();
        return this.repository.getAttributesByPage(searchFieldConditionList, paginationQuery.toPageQuery(), paginationQuery.toSortCondition());
    }
}
