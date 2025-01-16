package cn.devicelinks.framework.jdbc.repositorys;

import cn.devicelinks.framework.common.annotation.RegisterBean;
import cn.devicelinks.framework.common.pojos.Product;
import cn.devicelinks.framework.jdbc.core.JdbcRepository;
import org.springframework.jdbc.core.JdbcOperations;

import static cn.devicelinks.framework.jdbc.tables.TProduct.PRODUCT;

/**
 * The {@link Product} JDBC Repository
 *
 * @author 恒宇少年
 * @since 1.0
 */
@RegisterBean
public class ProductJdbcRepository extends JdbcRepository<Product, String> implements ProductRepository {
	public ProductJdbcRepository(JdbcOperations jdbcOperations) {
		super(PRODUCT, jdbcOperations);
	}
}
