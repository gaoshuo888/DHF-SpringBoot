package com.example.dhf_springboot.model.dhf_springboot;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数缓存类，数据库参数加载到内存中，参数若改变需要刷新！
 */
@Component
public class ParameterCache {

    private final JdbcTemplate jdbcTemplate;

    // 内存缓存，改成存 Double 类型
    private final Map<String, Double> PARAMETERS = new HashMap<>();

    public ParameterCache(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 初始化参数缓存
     * @PostConstruct注解表示该方法会在构造函数执行完成后自动调用
     */
    @PostConstruct
    public void loadParameters() {//用于初始化参数
        PARAMETERS.putAll(loadFromTable("SELECT name, value FROM parameters_basin"));
        PARAMETERS.putAll(loadFromTable("SELECT name, value FROM parameters_e"));
        PARAMETERS.putAll(loadFromTable("SELECT name, value FROM parameters_runoff"));
        PARAMETERS.putAll(loadFromTable("SELECT name, value FROM parameters_confluence"));
    }

    private Map<String, Double> loadFromTable(String sql) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Map<String, Double> map = new HashMap<>();
        for (Map<String, Object> row : rows) {
            String name = (String) row.get("name");
            Object valueObj = row.get("value");

            Double value = null;
            if (valueObj instanceof Number) {
                value = ((Number) valueObj).doubleValue();
            } else if (valueObj != null) {
                try {
                    value = Double.parseDouble(valueObj.toString());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("参数 '" + name + "' 的 value 不是数字类型，实际是：" + valueObj);
                }
            }
            map.put(name, value);
        }
        return map;
    }


    /**
     * 根据参数名称获取对应的值
     * @param name 参数名称
     * @return 参数值，可能为null
     */
    public Double getParameter(String name) {
        return PARAMETERS.get(name);
    }

    /**
     * 获取所有参数副本，防止外部直接修改内部缓存
     */
    public Map<String, Double> getAllParameters() {
        return new HashMap<>(PARAMETERS);
    }

    /**
     * 刷新缓存，重新从数据库加载
     */
    public synchronized void refresh() {
        PARAMETERS.clear();
        loadParameters();
    }

}
