package cn.devicelinks.console.model.search.module;

import cn.devicelinks.framework.common.web.SearchField;
import cn.devicelinks.framework.common.web.SearchFieldModule;
import cn.devicelinks.framework.common.web.SearchFieldModuleIdentifier;
import org.reflections.Reflections;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 检索字段模块工厂类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class SearchFieldModuleFactory implements InitializingBean {
    private static final Map<SearchFieldModuleIdentifier, SearchFieldModule> SEARCH_FIELD_MODULES = new LinkedHashMap<>();

    public static List<SearchField> getSearchFields(SearchFieldModuleIdentifier identifier) {
        return SEARCH_FIELD_MODULES.containsKey(identifier) ? SEARCH_FIELD_MODULES.get(identifier).getSearchFields() : Collections.emptyList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<? extends SearchFieldModule>> searchFieldModuleImplementations = findAllSearchFieldModuleImplementations();
        for (Class<? extends SearchFieldModule> implementation : searchFieldModuleImplementations) {
            SearchFieldModule searchFieldModule = implementation.getDeclaredConstructor().newInstance();
            if (searchFieldModule.supportIdentifier() != null) {
                SEARCH_FIELD_MODULES.put(searchFieldModule.supportIdentifier(), searchFieldModule);
            } else {
                throw new IllegalStateException("SearchFieldModule [" + implementation.getName() + "] must have a supportIdentifier()");
            }
        }
    }

    private static Set<Class<? extends SearchFieldModule>> findAllSearchFieldModuleImplementations() {
        Reflections reflections = new Reflections(SearchFieldModuleFactory.class.getPackage().getName());
        return reflections.getSubTypesOf(SearchFieldModule.class);
    }
}
