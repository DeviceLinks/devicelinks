package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.Ota;

import java.io.Serial;
import java.util.List;

/**
 * The {@link Ota} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TOta extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TOta OTA = new TOta("ota");

	private TOta(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column PRODUCT_ID = Column.withName("product_id").build();
	public final Column MODULE_ID = Column.withName("module_id").build();
	public final Column TYPE = Column.withName("type").typeMapper(ColumnValueMappers.OTA_PACKAGE_TYPE).build();
	public final Column VERSION = Column.withName("version").build();
	public final Column SIGN_ALGORITHM = Column.withName("sign_algorithm").typeMapper(ColumnValueMappers.SIGN_ALGORITHM).build();
	public final Column SIGN_WITH_KEY = Column.withName("sign_with_key").booleanValue().build();
	public final Column VERIFY = Column.withName("verify").booleanValue().build();
	public final Column UPGRADE_ITEM = Column.withName("upgrade_item").typeMapper(ColumnValueMappers.STRING_JOINER).build();
	public final Column ADDITION = Column.withName("addition").typeMapper(ColumnValueMappers.OTA_ADDITION).build();
	public final Column ENABLED = Column.withName("enabled").booleanValue().build();
	public final Column DELETED = Column.withName("deleted").booleanValue().build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();
	public final Column MARK = Column.withName("mark").build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, PRODUCT_ID, MODULE_ID, TYPE, VERSION, SIGN_ALGORITHM, SIGN_WITH_KEY, VERIFY, UPGRADE_ITEM, ADDITION, ENABLED, DELETED, CREATE_BY, CREATE_TIME, MARK);
    }
}