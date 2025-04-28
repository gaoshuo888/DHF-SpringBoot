package com.example.dhf_springboot.repository;

import com.example.dhf_springboot.entity.ParametersRunOff;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FileName: ParametersRunOffRepository.java
 * 产流参数数据访问层接口，数据库操作的接口
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/28
 */
public interface ParametersRunOffRepository extends JpaRepository<ParametersRunOff, Integer> {
    // 这里可以添加自定义查询方法
    // 例如：List<ParametersRunOff> findByName(String name);
}
