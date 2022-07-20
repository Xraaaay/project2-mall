package com.cskaoyan.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * String ↔ String[]
 * @author fanxing056
 * @date 2022/07/16 17:02
 */
@MappedTypes(String[].class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringArrayTypeHandler implements TypeHandler<String[]> {

    ObjectMapper objectMapper = new ObjectMapper();

    // 输入映射过程
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, String[] strings, JdbcType jdbcType) throws SQLException {

        String value = null;
        try {
            value = objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 为第几个占位符提供的值是什么
        preparedStatement.setString(index,value);
    }

    // 输出映射过程
    @Override
    public String[] getResult(ResultSet resultSet, String columnName) throws SQLException {
        // 获得结果
        String result = resultSet.getString(columnName);
        return transfer(result);
    }

    @Override
    public String[] getResult(ResultSet resultSet, int index) throws SQLException {
        // 获得结果
        String result = resultSet.getString(index);
        return transfer(result);
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        // 获得结果
        String result = callableStatement.getString(i);
        return transfer(result);
    }

    public String[] transfer(String result) {
        String[] strings = new String[0];
        // 使用jackson将字符串转换String[]
        try {
            strings = objectMapper.readValue(result, String[].class);
        } catch (IOException e) {
            return strings;
        }
        return strings;
    }
}
