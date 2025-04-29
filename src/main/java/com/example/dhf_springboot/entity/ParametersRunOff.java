package com.example.dhf_springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * FileName: ParametersRunOff.java
 * 数据库表,产流参数类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@Entity
@Data
@Table(name = "parameters_runoff")
public class ParametersRunOff {
    @Id
    private Integer id;
    private String name;
    private Double value;//Double类型,数值可以为null
    private Double min;
    private Double max;
}
