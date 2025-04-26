package com.example.dhf_springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * FileName: Parameter.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/25
 */

@Data
@Entity
@Table(name = "parameter")
public class Parameter {
    @Id
    private Integer id;
    @JsonProperty("E1")//用于json序列化时的字段名，否则会是小写
    private double E1;
    @JsonProperty("E2")//用于json序列化时的字段名，规范中属性为小驼峰
    private double E2;
}
