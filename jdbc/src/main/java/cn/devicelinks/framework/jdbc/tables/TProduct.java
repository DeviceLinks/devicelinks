/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.devicelinks.framework.jdbc.tables;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import cn.devicelinks.framework.common.ProductStatus;
import cn.devicelinks.framework.jdbc.core.definition.Column;
import cn.devicelinks.framework.jdbc.core.definition.TableImpl;
import cn.devicelinks.framework.jdbc.ColumnValueMappers;
import cn.devicelinks.framework.common.pojos.Product;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@link Product} TableImpl
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class TProduct extends TableImpl {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;
    public static final TProduct PRODUCT = new TProduct("product");

    private TProduct(String tableName) {
        super(tableName);
    }

    public final Column ID = Column.withName("id").primaryKey().build();
    public final Column NAME = Column.withName("name").build();
    public final Column PRODUCT_KEY = Column.withName("product_key").build();
    public final Column PRODUCT_SECRET = Column.withName("product_secret").build();
    public final Column DEVICE_TYPE = Column.withName("device_type").typeMapper(ColumnValueMappers.DEVICE_TYPE).build();
    public final Column DEVICE_PROFILE_ID = Column.withName("device_profile_id").build();
    public final Column NETWORKING_AWAY = Column.withName("networking_away").typeMapper(ColumnValueMappers.DEVICE_NETWORKING_AWAY).build();
    public final Column ACCESS_GATEWAY_PROTOCOL = Column.withName("access_gateway_protocol").typeMapper(ColumnValueMappers.ACCESS_GATEWAY_PROTOCOL).build();
    public final Column DATA_FORMAT = Column.withName("data_format").typeMapper(ColumnValueMappers.DATA_FORMAT).build();
    public final Column DYNAMIC_REGISTRATION = Column.withName("dynamic_registration").booleanValue().build();
    public final Column STATUS = Column.withName("status").typeMapper(ColumnValueMappers.PRODUCT_STATUS).defaultValue(() -> ProductStatus.Development).build();
    public final Column DELETED = Column.withName("deleted").booleanValue().defaultValue(() -> Boolean.FALSE).build();
    public final Column DESCRIPTION = Column.withName("description").build();
    public final Column CREATE_BY = Column.withName("create_by").build();
    public final Column CREATE_TIME = Column.withName("create_time").localDateTimeValue().defaultValue(LocalDateTime::now).build();

    @Override
    public List<Column> getColumns() {
        return List.of(ID, NAME, PRODUCT_KEY, PRODUCT_SECRET, DEVICE_TYPE, DEVICE_PROFILE_ID, NETWORKING_AWAY, ACCESS_GATEWAY_PROTOCOL, DATA_FORMAT, DYNAMIC_REGISTRATION, STATUS, DELETED, DESCRIPTION, CREATE_BY, CREATE_TIME);
    }
}