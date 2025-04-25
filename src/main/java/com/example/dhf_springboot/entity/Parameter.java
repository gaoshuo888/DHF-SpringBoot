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
    private double E1;//默认驼峰转下划线
    private double E2;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", E1='" + E1 + '\'' +
                ", E2='" + E2 + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getE1() {
        return E1;
    }

    public void setE1(double e1) {
        E1 = e1;
    }

    public double getE2() {
        return E2;
    }

    public void setE2(double e2) {
        E2 = e2;
    }
}
