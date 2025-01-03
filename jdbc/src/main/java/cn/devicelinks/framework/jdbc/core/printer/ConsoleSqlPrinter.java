/*
 *   Copyright (C) 2024  恒宇少年
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

package cn.devicelinks.framework.jdbc.core.printer;

import cn.devicelinks.framework.jdbc.core.RepositoryMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制台打印SQL
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class ConsoleSqlPrinter implements SqlPrinter {
    /**
     * logger instance
     */
    private final Logger logger;
    private static final String DELIMITER = ", ";
    private static final String PRINT_NULL_STRING = "null";

    public ConsoleSqlPrinter(Class<?> consoleClass) {
        this.logger = LoggerFactory.getLogger(consoleClass);
    }

    @Override
    public void print(RepositoryMethod method, String sql, Object[] parameterValues, List<Map<String, Object>> resultRowMap, int affectedRows) {
        this.logger.debug("===>\tPreparing: {}", sql);
        if (!ObjectUtils.isEmpty(parameterValues)) {
            String parameters = Arrays.stream(parameterValues)
                    .map(sqlParameterValue -> {
                        if (sqlParameterValue != null) {
                            String typeName = sqlParameterValue.getClass().getSimpleName();
                            return sqlParameterValue + "(" + typeName + ")";
                        }
                        return PRINT_NULL_STRING;
                    })
                    .collect(Collectors.joining(DELIMITER));
            this.logger.debug("===>\tParameters: {}", parameters);
        }
        if (RepositoryMethod.Query == method) {
            if (!ObjectUtils.isEmpty(resultRowMap)) {
                resultRowMap.forEach(columnValueMap -> logger.debug("===>\tRow: {}", columnValueMap));
            }
        }
        this.logger.debug("===>\t{} Rows: {}", method.toString(), affectedRows);
    }
}
