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

import cn.devicelinks.framework.common.pojos.ChartDataFields;
import com.google.common.base.CaseFormat;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Repository生成器
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class RepositoryGenerator {
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String POJO_PATH = PROJECT_PATH + "/common/target/classes/cn/devicelinks/framework/common/pojos";
    private static final String POJO_PACKAGE = "cn.devicelinks.framework.common.pojos";
    private static final String REPOSITORY_PATH = PROJECT_PATH + "/jdbc/src/main/java/cn/devicelinks/framework/jdbc/repositorys";

    public static void main(String[] args) throws Exception {
        generateSingleClass();
    }

    static void generateSingleClass() throws Exception {
        // entity class
        Class entityClass = ChartDataFields.class;

        // Repository
        String repository = generateRepository(entityClass);
        writing(repository, entityClass.getSimpleName() + "Repository");

        // JdbcRepository
        String jdbcRepository = generateJdbcRepository(entityClass);
        writing(jdbcRepository, entityClass.getSimpleName() + "JdbcRepository");
    }

    static void generateAllClass() throws Exception {
        Path pojosPath = Paths.get(POJO_PATH);
        Files.list(pojosPath).forEach(pojoPath -> {
            String className = POJO_PACKAGE + "." + pojoPath.getFileName().toString().replace(".class", "");
            try {
                Class entityClass = Class.forName(className);

                if (className.indexOf("Addition") > 0) {
                    return;
                }
                // Repository
                String repository = generateRepository(entityClass);
                writing(repository, entityClass.getSimpleName() + "Repository");

                // JdbcRepository
                String jdbcRepository = generateJdbcRepository(entityClass);
                writing(jdbcRepository, entityClass.getSimpleName() + "JdbcRepository");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    static String generateRepository(Class<?> entityClass) {
        String lineSeparator = System.lineSeparator();
        String className = entityClass.getSimpleName();
        StringBuffer buffer = new StringBuffer();
        buffer.append("package cn.devicelinks.framework.jdbc.repositorys;").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("import " + entityClass.getName() + ";").append(lineSeparator);
        buffer.append("import cn.devicelinks.framework.jdbc.core.Repository;").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("/**" + lineSeparator +
                " * The {@link " + className + "} Repository" + lineSeparator +
                " *" + lineSeparator +
                " * @author 恒宇少年" + lineSeparator +
                " * @since 1.0" + lineSeparator +
                " */");
        buffer.append(lineSeparator);
        buffer.append("public interface " + className + "Repository extends Repository<" + className + ", String> {").append(lineSeparator);
        buffer.append("\t//...").append(lineSeparator);
        buffer.append("}").append(lineSeparator);
        return buffer.toString();
    }

    static String generateJdbcRepository(Class<?> entityClass) {
        String lineSeparator = System.lineSeparator();
        String className = entityClass.getSimpleName();
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, className);
        String tableNameUpper = tableName.toUpperCase();
        StringBuffer buffer = new StringBuffer();
        buffer.append("package cn.devicelinks.framework.jdbc.repositorys;").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("import cn.devicelinks.framework.common.annotation.RegisterBean;").append(lineSeparator);
        buffer.append("import " + entityClass.getName() + ";").append(lineSeparator);
        buffer.append("import cn.devicelinks.framework.jdbc.core.JdbcRepository;").append(lineSeparator);
        buffer.append("import org.springframework.jdbc.core.JdbcOperations;").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("import static cn.devicelinks.framework.jdbc.tables.T" + className + "." + tableNameUpper + ";").append(lineSeparator);
        buffer.append(lineSeparator);
        buffer.append("/**" + lineSeparator +
                " * The {@link " + className + "} JDBC Repository" + lineSeparator +
                " *" + lineSeparator +
                " * @author 恒宇少年" + lineSeparator +
                " * @since 1.0" + lineSeparator +
                " */");
        buffer.append(lineSeparator);
        buffer.append("@RegisterBean").append(lineSeparator);
        buffer.append("public class " + className + "JdbcRepository extends JdbcRepository<" + className + ", String> implements " + className + "Repository {").append(lineSeparator);
        buffer.append("\tpublic " + className + "JdbcRepository(JdbcOperations jdbcOperations) {").append(lineSeparator);
        buffer.append("\t\tsuper(" + tableNameUpper + ", jdbcOperations);").append(lineSeparator);
        buffer.append("\t}").append(lineSeparator);
        buffer.append("}").append(lineSeparator);
        return buffer.toString();
    }

    static void writing(String content, String className) throws Exception {
        File file = new File(REPOSITORY_PATH + "/" + className + ".java");

        if (!file.exists()) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(content);
                System.out.println(file.getPath() + ", content write successfully.");
            }
        }
    }
}
