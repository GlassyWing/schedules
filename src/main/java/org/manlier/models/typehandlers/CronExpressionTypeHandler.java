package org.manlier.models.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.quartz.CronExpression;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by manlier on 2017/6/5.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(CronExpression.class)
public class CronExpressionTypeHandler extends BaseTypeHandler<CronExpression> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CronExpression parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCronExpression());
    }

    @Override
    public CronExpression getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getString(columnName) == null)
            return null;
        try {
            return new CronExpression(rs.getString(columnName));
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public CronExpression getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getString(columnIndex) == null)
            return null;
        try {
            return new CronExpression(rs.getString(columnIndex));
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public CronExpression getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getString(columnIndex) == null)
            return null;
        try {
            return new CronExpression(cs.getString(columnIndex));
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }
}
