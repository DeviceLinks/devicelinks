package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.FunctionModuleTemplate;

import java.io.Serial;
import java.util.List;

/**
 * The {@link FunctionModuleTemplate} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TFunctionModuleTemplate extends TableImpl {
	@Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
	public static final TFunctionModuleTemplate FUNCTION_MODULE_TEMPLATE = new TFunctionModuleTemplate("function_module_template");

	private TFunctionModuleTemplate(String tableName) {
        super(tableName);
    }

	public final Column ID = Column.withName("id").primaryKey().build();
	public final Column MODULE_ID = Column.withName("module_id").build();
	public final Column NAME = Column.withName("name").build();
	public final Column DELETED = Column.withName("deleted").booleanValue().build();
	public final Column CREATE_BY = Column.withName("create_by").build();
	public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().build();

	@Override
    public List<Column> getColumns() {
        return List.of(ID, MODULE_ID, NAME, DELETED, CREATE_BY, CREATE_TIME);
    }
}