package com.example.dhf_springboot.repository;

import com.example.dhf_springboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileName: BookRepository.java
 * 图书数据访问层接口，数据库操作的接口
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
    //<Book, Integer>表示Book类的主键类型是Integer
    // 这里可以添加自定义查询方法
    // 例如：List<Book> findByAuthor(String author);
}
