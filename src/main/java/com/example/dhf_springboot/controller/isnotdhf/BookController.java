package com.example.dhf_springboot.controller.isnotdhf;

import com.example.dhf_springboot.entity.isnotdhf.Book;
import com.example.dhf_springboot.repository.isnotdhf.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * FileName: BookController.java
 * 负责处理图书数据的增删改查请求
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
