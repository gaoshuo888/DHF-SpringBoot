package com.example.dhf_springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * FileName: ParametersE.java
 * 数据库表蒸发参数类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@Entity
@Data
@Table(name = "parameters_e")
public class ParametersE {
    @Id
    private Integer id;
    private String name;
    private Double value;
    private Double min;
    private Double max;
}
