package nu.ssis.a18mosu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.service.BookService;
import nu.ssis.a18mosu.service.LoanService;

@RestController
public class BookApi {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private LoanService loanService;

	@GetMapping("/v1/book")
	public ResponseEntity<?> bookStatus(@RequestParam("bookId") String bookId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BookStatus status =  loanService.status(bookId);
		resultMap.put("status", status);
		if(status != BookStatus.NOT_FOUND) {
			resultMap.put("book", bookService.getBook(bookId).getBook());
		}
		return ResponseEntity.ok().body(resultMap);
	}
	
}