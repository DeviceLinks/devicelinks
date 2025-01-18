package cn.devicelinks.console.model.parameter;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页参数
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Getter
@Setter
public class PageableParameter {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_INDEX = 1;

    private int pageIndex = DEFAULT_PAGE_INDEX;

    private int pageSize = DEFAULT_PAGE_SIZE;
}
