package com.example.dhf_springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * FileName: ParametersConfluence.java
 * 汇流参数实体类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
@Entity
@Data
@Table(name = "parameters_confluence")
public class ParametersConfluence {
    @Id
    private Integer id;
    private String name;
    private Double value;
}
