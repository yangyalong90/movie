package com.xxx.common.mybatis.aspect.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxx.common.mybatis.mapper.SelectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.xxx.common.mybatis.aspect.MapperAspect.MAPPER_T_LOCAL;

@Component
public class SelectMapperExecutor<T> implements SelectMapper<T> {

    private JdbcTemplate jdbcTemplate;

    public SelectMapperExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<?> selectList(Object entity) {

        List<?> list = jdbcTemplate.query("select * from xxx_user", new ObjectRowMapper());
        return list;
    }

    @Override
    public T selectById(Serializable id) {
        List<?> list = jdbcTemplate.query("select * from xxx_user where id = ?", new ObjectRowMapper(), id);
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() != 1) {
            throw new RuntimeException("search too many record");
        }
        return (T) list.get(0);
    }

    class ObjectRowMapper implements RowMapper<Object> {
        private final List<String> columns = new LinkedList<>();
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (columns.isEmpty()) {
                for (int i = 1; i <= columnCount; i++) {
                    columns.add(metaData.getColumnName(i));
                }
            }

            Map<String, Object> data = new HashMap<>();
            columns.forEach(column -> {
                Object object;
                try {
                    object = rs.getObject(column);
                } catch (SQLException e) {
                    return;
                }
                data.put(column, object);
            });
            if (data.isEmpty()) {
                return null;
            }
            return JSONObject.parseObject(JSON.toJSONString(data), MAPPER_T_LOCAL.get());
        }
    }

}
