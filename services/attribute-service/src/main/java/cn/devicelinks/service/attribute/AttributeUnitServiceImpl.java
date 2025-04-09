package cn.devicelinks.service.attribute;

import cn.devicelinks.framework.common.pojos.AttributeUnit;
import cn.devicelinks.framework.jdbc.BaseServiceImpl;
import cn.devicelinks.framework.jdbc.repositorys.AttributeUnitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.devicelinks.framework.jdbc.tables.TAttributeUnit.ATTRIBUTE_UNIT;

/**
 * 属性单位业务逻辑接口实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Service
@Slf4j
public class AttributeUnitServiceImpl extends BaseServiceImpl<AttributeUnit, String, AttributeUnitRepository> implements AttributeUnitService {
    public AttributeUnitServiceImpl(AttributeUnitRepository repository) {
        super(repository);
    }

    @Override
    public List<AttributeUnit> selectEfficientList() {
        return this.repository.select(ATTRIBUTE_UNIT.ENABLED.eq(Boolean.TRUE),
                ATTRIBUTE_UNIT.DELETED.eq(Boolean.FALSE));
    }
}
