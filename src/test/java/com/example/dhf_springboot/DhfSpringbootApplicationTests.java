package com.example.dhf_springboot;

import com.example.dhf_springboot.entity.Book;
import com.example.dhf_springboot.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DhfSpringbootApplicationTests {

	@Autowired//Autowired注入BookRepository
	private BookRepository bookRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void save() {//保存数据--可以添加，修改（ById）
		Book book = new Book();
		book.setId(141);
		book.setName("Java编程思想");
		book.setAuthor("Bruce Eckel");
		bookRepository.save(book);
		System.out.println(book);
	}
	@Test
	void delete() {//删除数据
		bookRepository.deleteById(129);
		System.out.println("删除成功");
	}

}
