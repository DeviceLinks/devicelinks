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

package cn.devicelinks.framework.common.jackson2;

import cn.devicelinks.framework.common.DeviceLinksVersion;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.deser.key.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;

import java.io.Serial;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Jackson的时间转换模块配置
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class DeviceLinksSerializableModule extends SimpleModule {
    @Serial
    private static final long serialVersionUID = DeviceLinksVersion.SERIAL_VERSION_UID;

    public DeviceLinksSerializableModule() {
        super(DeviceLinksSerializableModule.class.getName(), new Version(1, 0, 0, null, null, null));
        // From JavaTimeModule
        this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        this.addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
        this.addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
        this.addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        this.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        this.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        this.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        this.addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
        this.addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        this.addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        this.addDeserializer(Year.class, YearDeserializer.INSTANCE);
        this.addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
        this.addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        this.addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
        this.addSerializer(Duration.class, DurationSerializer.INSTANCE);
        this.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        this.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        this.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        this.addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        this.addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
        this.addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        this.addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        this.addSerializer(Period.class, new ToStringSerializer(Period.class));
        this.addSerializer(Year.class, YearSerializer.INSTANCE);
        this.addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);
        this.addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
        this.addSerializer(ZoneId.class, new ZoneIdSerializer());
        this.addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));
        this.addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);
        this.addKeyDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(YearMonth.class, YearMonthKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
        this.addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);

        // OnSecurity Customize

        // LocalDateTime
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // LocalDate
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        // LocalTime
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));

    }
}
