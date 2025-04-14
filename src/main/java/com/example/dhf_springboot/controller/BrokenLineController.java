package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.Book;
import com.example.dhf_springboot.entity.BrokenLine;
import com.example.dhf_springboot.repository.BrokenLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * FileName: Book2Controller.java
 * 负责处理折线图数据的增删改查请求
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@RestController
@RequestMapping("/chart")
public class BrokenLineController {
    @Autowired
    private BrokenLineRepository brokenLineRepository;

    @GetMapping("/list")//http://127.0.0.1:8082/chart/list中显示结果,用于折线图展示的数据
    public Object getAllBooks() {
        return brokenLineRepository.findAll();
    }
    @GetMapping("/list/{pageNumber}")//数据库，增删改查。
    public Page<BrokenLine> getAllBooks(@PathVariable("pageNumber") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1, 6); // 分页请求，第?页，每页6条数据
        return brokenLineRepository.findAll(pageable);
    }
    @PostMapping("/addQ")//POST请求，添加图书
    public BrokenLine addBook(@RequestBody BrokenLine brokenLine) {
        return brokenLineRepository.save(brokenLine);
    }
    @DeleteMapping("/delete/{id}")//DELETE请求，删除图书
    public void deleteBook(@PathVariable("id") int id) {
        brokenLineRepository.deleteById(id);
    }
}
