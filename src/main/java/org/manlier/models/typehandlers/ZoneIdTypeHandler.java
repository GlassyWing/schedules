package org.manlier.models.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

/**
 * ZoneId类型处理器
 * Created by manlier on 2017/6/13.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(ZoneId.class)
public class ZoneIdTypeHandler extends BaseTypeHandler<ZoneId> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ZoneId parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public ZoneId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getString(columnName) == null)
            return null;
        return ZoneId.of(rs.getString(columnName));
    }

    @Override
    public ZoneId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getString(columnIndex) == null)
            return null;
        return ZoneId.of(rs.getString(columnIndex));
    }

    @Override
    public ZoneId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getString(columnIndex) == null)
            return null;
        return ZoneId.of(cs.getString(columnIndex));
    }
}
