package com.example.dhf_springboot.controller;

import com.example.dhf_springboot.entity.Book;
import com.example.dhf_springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileName: BookController.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

//    @GetMapping("/list/{}")//http://127.0.0.1:8082/book/list中显示结果
    @GetMapping("/list/{pageNumber}")
    public Page<Book> getAllBooks(@PathVariable("pageNumber") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1, 6); // 分页请求，第?页，每页6条数据
        return bookRepository.findAll(pageable);
    }
    @PostMapping("/add")//POST请求，添加图书
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
    @DeleteMapping("/delete/{id}")//DELETE请求，删除图书
    public void deleteBook(@PathVariable("id") int id) {
        bookRepository.deleteById(id);
    }

}
