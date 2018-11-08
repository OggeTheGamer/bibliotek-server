package nu.ssis.a18mosu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nu.ssis.a18mosu.datatransferobject.BookRegisterDTO;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.service.BookService;
import nu.ssis.a18mosu.service.LoanService;

@RunWith(SpringJUnit4ClassRunner.class)
public class BiblotekApplicationTests {

	@Autowired
	private BookService bookService;
	@Autowired
	private LoanService loanService;

	@Test
	public void asd() {
		BookRegisterDTO bookDto = new BookRegisterDTO();
		bookDto.setBookId(0);
		bookDto.setIsbn("0");
		Assert.assertSame(BookStatus.NOT_FOUND, loanService.bookStatus(bookDto.getBookId()));
		bookService.registerBook(bookDto);
		Assert.assertSame(BookStatus.AVALIABLE, loanService.bookStatus(bookDto.getBookId()));
	}
}
