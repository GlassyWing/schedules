package org.manlier.models.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

/**
 * Created by manlier on 2017/6/10.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Duration.class)
public class DurationTypeHandler extends BaseTypeHandler<Duration> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Duration parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public Duration getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getString(columnName) == null)
            return null;
        return Duration.parse(rs.getString(columnName));
    }

    @Override
    public Duration getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getString(columnIndex) == null)
            return null;
        return Duration.parse(rs.getString(columnIndex));
    }

    @Override
    public Duration getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getString(columnIndex) == null)
            return null;
        return Duration.parse(cs.getString(columnIndex));
    }
}
