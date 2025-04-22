package cn.devicelinks.component.web.search;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 检索字段模块工厂类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class SearchFieldModuleFactory implements InitializingBean, ApplicationContextAware {
    private static final Map<SearchFieldModuleIdentifier, SearchFieldModule> SEARCH_FIELD_MODULES = new LinkedHashMap<>();
    private ApplicationContext applicationContext;

    public static List<SearchField> getSearchFields(SearchFieldModuleIdentifier identifier) {
        return SEARCH_FIELD_MODULES.containsKey(identifier) ? SEARCH_FIELD_MODULES.get(identifier).getSearchFields() : Collections.emptyList();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, SearchFieldModule> searchFieldModuleBeanMap = applicationContext.getBeansOfType(SearchFieldModule.class);
        searchFieldModuleBeanMap.keySet().forEach(beanName -> {
            SearchFieldModule searchFieldModule = searchFieldModuleBeanMap.get(beanName);
            if (searchFieldModule.supportIdentifier() != null) {
                SEARCH_FIELD_MODULES.put(searchFieldModule.supportIdentifier(), searchFieldModule);
            } else {
                throw new IllegalStateException("SearchFieldModule [" + searchFieldModule.getClass().getName() + "] must have a supportIdentifier()");
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
