package com.example.dhf_springboot.repository;

import com.example.dhf_springboot.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * FileName: BookRepositoryTest.java
 * 类的详细说明
 *
 * @author GaoShuo
 * @version 1.0.0
 * @Date 2025/4/9
 */
@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Test
    void testFindByAuthor() {
        System.out.println(bookRepository.findAll());
//        List<Book> books = bookRepository.findAll();
//        for (Book book : books) {
//            System.out.println(book.toString());
//        }

    }
}