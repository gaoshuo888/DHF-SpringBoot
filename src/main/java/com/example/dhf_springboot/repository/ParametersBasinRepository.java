package com.example.dhf_springboot.repository;

import com.example.dhf_springboot.entity.ParametersBasin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileName: ParametersBasinRepository.java
 * 流域参数数据访问层,操作数据库
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/29
 */
public interface ParametersBasinRepository extends JpaRepository<ParametersBasin, Integer> {
    // 这里可以添加自定义查询方法
    // 例如：List<ParametersBasin> findByName(String name);
}
