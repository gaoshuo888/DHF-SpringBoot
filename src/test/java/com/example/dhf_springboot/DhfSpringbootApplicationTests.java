package com.example.dhf_springboot;

import com.example.dhf_springboot.entity.isnotdhf.Book;
import com.example.dhf_springboot.entity.isnotdhf.BrokenLine;
import com.example.dhf_springboot.repository.isnotdhf.BookRepository;
import com.example.dhf_springboot.repository.isnotdhf.BrokenLineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DhfSpringbootApplicationTests {

	@Autowired//Autowired注入BookRepository
	private BookRepository bookRepository;
	@Autowired
	private BrokenLineRepository brokenLineRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void save() {//保存数据--可以添加，修改（ById）
		Book book = new Book();
//		book.setId(141);
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

	@Test
	void saveQ() {//保存数据--可以添加，修改（ById）
		BrokenLine brokenLine = new BrokenLine();
//		brokenLine.setId(3);
//		brokenLine.setxData("星期on完全e");
		brokenLine.setXData("星期on完全e");//@Data注解,xData--> setXData
		brokenLine.setYData(100F);
		brokenLineRepository.save(brokenLine);
		System.out.println(brokenLine);

	}

}
