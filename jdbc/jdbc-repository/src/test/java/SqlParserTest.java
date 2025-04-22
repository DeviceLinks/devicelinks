import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLStatementParser;

/**
 * Sql解析单元测试类
 */
public class SqlParserTest {

    public static void main(String[] args) {
        String sql = "SELECT a.*, (SELECT count(id) FROM attribute WHERE attribute.pid = a.id) child_count FROM attribute a";

        SQLStatementParser parser = new SQLStatementParser(sql);
        SQLStatement stmt = parser.parseStatement();
        SQLSelectQueryBlock query = (SQLSelectQueryBlock) ((SQLSelectStatement) stmt).getSelect().getQuery();

        System.out.println("query：" + query);

        query.setWhere(new SQLBinaryOpExpr(new SQLIdentifierExpr("a"), SQLBinaryOperator.Equality, new SQLIdentifierExpr("b")));

        System.out.println("stmt：" + stmt);
    }
}