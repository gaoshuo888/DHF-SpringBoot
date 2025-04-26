package com.example.dhf_springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * FileName: Book.java
 * 图书类，实体类接收数据库数据
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@Entity
@Data
@Table(name = "book")//@Table(name = "book") // 如果表名与类名相同，可以省略
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;
    private String name;
    private String author;
}
