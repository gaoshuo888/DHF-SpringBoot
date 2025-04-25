package com.example.dhf_springboot.repository;

import com.example.dhf_springboot.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileName: ParameterRepository.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/25
 */
public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
    // 这里可以添加自定义查询方法
    // 例如：List<Parameter> findByParameterName(String parameterName);
}
