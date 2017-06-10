package org.manlier.models.typehandlers;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by manlier on 2017/6/4.
 */

/**
 * 设置timestamp到字符串的映射
 */
@MappedJdbcTypes(JdbcType.TIMESTAMP)
@MappedTypes(String.class)
public class DateStringTypeHandler extends BaseTypeHandler<String> {

    private final static String CUSTOM_FORMAT_STR = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";

    private final static String TIMESTAMP_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    private SimpleDateFormat
            customFormat,    // 自定义时间格式
            timestampFormat; // 时间戳格式（保存到数据库中的格式）

    public DateStringTypeHandler(String customFormatString) {
        this.customFormat = new SimpleDateFormat(customFormatString);
        this.timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT_STR);
    }

    public DateStringTypeHandler(String customFormatString, String timestampFormatString) {
        this.customFormat = new SimpleDateFormat(customFormatString);
        this.timestampFormat = new SimpleDateFormat(timestampFormatString);
    }

    public DateStringTypeHandler() {
        this.customFormat = new SimpleDateFormat(CUSTOM_FORMAT_STR);
        this.timestampFormat = new SimpleDateFormat(TIMESTAMP_FORMAT_STR);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {

        try {
            String tsStr = timestampFormat.format(customFormat.parse(parameter));
            Timestamp timestamp = Timestamp.valueOf(tsStr);
            ps.setTimestamp(i, timestamp);
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        if (timestamp == null) return null;
        return customFormat.format(timestamp);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        if (timestamp == null) return null;
        return customFormat.format(timestamp);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        if (timestamp == null) return null;
        return customFormat.format(timestamp);

    }
}
