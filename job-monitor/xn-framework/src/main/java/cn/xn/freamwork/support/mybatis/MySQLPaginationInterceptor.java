package cn.xn.freamwork.support.mybatis;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;

/**
 * MySQL 分页拦截器
 */
@Intercepts( {@Signature( type = StatementHandler.class, method = "prepare", args = {Connection.class} )} )
public class MySQLPaginationInterceptor extends AbstractPaginationInterceptor {

	@Override
	public String toPaginationSQL(String originalSql, RowBounds rowBounds) {
		if (StringUtils.isEmpty(originalSql) || originalSql.toLowerCase().contains("limit")) {
			return originalSql;
		}
		originalSql = toLineSql(originalSql);
		return originalSql + " limit " + rowBounds.getOffset() + " ," + rowBounds.getLimit();
	}
}
