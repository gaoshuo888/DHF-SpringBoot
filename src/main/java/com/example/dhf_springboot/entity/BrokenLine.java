package com.example.dhf_springboot.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

/**
 * FileName: Book2.java
 * 折线图数据类
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@Entity
@Data
@Table(name = "brokenline") // 如果表名与类名相同，可以省略
public class BrokenLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增，添加数据时自动生成，必需！！
    private Integer id;
    @Column(name = "x_axis")  // 显式指定数据库列名
    @JsonProperty("xData")//用于json序列化时的字段名，否则会是小写
    private String xData;//横坐标数据
    @JsonProperty("yData")//用于json序列化时的字段名，否则会是小写
    @Column(name = "y_axis")  // 显式指定数据库列名
    private Float yData;//纵坐标数据
}
