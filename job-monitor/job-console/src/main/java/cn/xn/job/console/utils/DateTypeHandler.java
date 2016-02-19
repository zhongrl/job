package cn.xn.job.console.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/***
 * 
 * 项目名称：activity-service
 * 
 * 类名称：DateTypeHandler
 * 
 * 类描述： Java Date <--> Jdbc DECIMAL
 * 
 * 创建人： zhou'sheng
 * 
 * 创建时间：2015年12月28日 上午1:43:44
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 * 
 * @param <T>
 */
@MappedJdbcTypes(JdbcType.DECIMAL)
@MappedTypes(Date.class)
public class DateTypeHandler extends BaseTypeHandler<Date> {

	@Override
	public Date getNullableResult(ResultSet rs, String key) throws SQLException {
		Long dateL = rs.getLong(key);
		if (null != dateL && dateL != 0) {
			return new Date(dateL);
		}
		return null;
	}

	@Override
	public Date getNullableResult(ResultSet rs, int key) throws SQLException {
		Long dateL = rs.getLong(key);
		if (null != dateL && dateL != 0) {
			return new Date(dateL);
		}
		return null;
	}

	@Override
	public Date getNullableResult(CallableStatement cs, int key) throws SQLException {
		return cs.getDate(key);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int key, Date date, JdbcType type) throws SQLException {
		if (JdbcType.DECIMAL.equals(type)) {
			ps.setLong(key, date.getTime());
		}
	}

}
