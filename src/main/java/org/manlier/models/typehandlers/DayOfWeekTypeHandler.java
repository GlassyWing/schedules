package org.manlier.models.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Created by manlier on 2017/6/13.
 */
@MappedTypes(DayOfWeek.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class DayOfWeekTypeHandler extends BaseTypeHandler<DayOfWeek> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DayOfWeek parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());
    }

    @Override
    public DayOfWeek getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getString(columnName) == null)
            return null;
        return DayOfWeek.valueOf(rs.getString(columnName));
    }

    @Override
    public DayOfWeek getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getString(columnIndex) == null)
            return null;
        return DayOfWeek.valueOf(rs.getString(columnIndex));
    }

    @Override
    public DayOfWeek getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (cs.getString(columnIndex) == null)
            return null;
        return DayOfWeek.valueOf(cs.getString(columnIndex));
    }
}
