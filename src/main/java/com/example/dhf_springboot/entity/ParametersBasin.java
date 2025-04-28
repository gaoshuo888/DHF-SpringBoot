package com.example.dhf_springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * FileName: ParametersBasin.java
 * 流域参数实体类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/29
 */
@Entity
@Data
@Table(name = "parameters_basin")
public class ParametersBasin {
    @Id
    private Integer id;
    private String name;
    private Double value;
}
