package com.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bean.Book;

public interface IBookService {

	@PreAuthorize("hasRole('ROLE_WRITE')")
	//@PreAuthorize 입력값을 제어가능하다 
	void addBook(Book book);
	
	
	@PostAuthorize("returnObject.owner == authentication.name")
	
	//@PostAuthorize리턴값을 제어 가능하다
	//ex, 글을썼는데 수정할때 작성자랑 다른경우 막아버림. 
	//일단 놀게는 해주는데 집에갈때 막음
	Book getBook();
	/*
	   현재 로그인한 계정 : hong 
	   Book b = new Book("구운몽","kglim")
	   
	   "kglim" != "hong" > false 접근권한 예외 
	   return  b; 
	*/
	
	/*
	 Book bo = new Book("홍길동전","kglim")
	 (bo.kglim == "kglim")
	 delelteBook(bo)
	 */
	@PreAuthorize("#book.owner == authentication.name")
	//함수를 태우기전에 미리 검사 @PreAuthorize 
	//book객체안에 들어있는 멤버필드를 미리 꺼내서 (소유자멤버필드) authentication.name로그인 아이디랑 같으면 태우고, 아니면 안태우고
	void deleteBook(Book book);
}









