package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import cn.devicelinks.framework.jdbc.tables.TProduct;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * 设备产品数据接口JDBC实现类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class ProductJdbcRepository extends JdbcRepository<Product, String> implements ProductRepository {
    public ProductJdbcRepository(JdbcOperations jdbcOperations) {
        super(TProduct.PRODUCT, jdbcOperations);
    }
}
