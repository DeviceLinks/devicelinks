package cn.devicelinks.framework.common.web;

import java.util.List;

/**
 * 检索字段模块
 * <p>
 * 定义一个功能模块下的全部检索字段以及所支持的功能模块标识
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface SearchFieldModule {

    SearchFieldModuleIdentifier supportIdentifier();

    List<SearchField> getSearchFields();
}
