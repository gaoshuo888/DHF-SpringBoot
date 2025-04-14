package com.example.dhf_springboot.entity;

import jakarta.persistence.*;

/**
 * FileName: Book2.java
 * 折线图数据类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@Entity
@Table(name = "brokenline") // 如果表名与类名相同，可以省略
public class BrokenLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增，添加数据时自动生成，必需！！
    private Integer id;
    @Column(name = "x_axis")  // 显式指定数据库列名
    private String xData;//横坐标数据
    @Column(name = "y_axis")  // 显式指定数据库列名
    private Float yData;//纵坐标数据

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getxData() {
        return xData;
    }

    public void setxData(String xData) {
        this.xData = xData;
    }

    public Float getyData() {
        return yData;
    }

    public void setyData(Float yData) {
        this.yData = yData;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", xData='" + xData + '\'' +
                ", yData='" + yData + '\'' +
                '}';
    }
}
