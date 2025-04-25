package com.example.dhf_springboot.entity;

import jakarta.persistence.Column;
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
    private String E1;//默认驼峰转下划线
    private String E2;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", parameterName='" + E1 + '\'' +
                ", parameterValue='" + E2 + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getE1() {
        return E1;
    }

    public void setE1(String parameterName) {
        this.E1 = parameterName;
    }

    public String getE2() {
        return E2;
    }

    public void setE2(String parameterValue) {
        this.E2 = parameterValue;
    }
}
