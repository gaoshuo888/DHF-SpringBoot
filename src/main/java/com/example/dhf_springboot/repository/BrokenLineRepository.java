package com.example.dhf_springboot.repository;

import com.example.dhf_springboot.entity.BrokenLine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileName: Book2Repository.java
 * 折线图数据访问层接口，数据库操作的接口
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
public interface BrokenLineRepository extends JpaRepository<BrokenLine, Integer> {
    // 这里可以添加自定义查询方法
    // 例如：List<Book2> findByAuthor(String author);
}
