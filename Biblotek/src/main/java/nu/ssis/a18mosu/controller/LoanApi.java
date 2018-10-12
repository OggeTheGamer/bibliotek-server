package nu.ssis.a18mosu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nu.ssis.a18mosu.datatransferobject.BookDTO;
import nu.ssis.a18mosu.datatransferobject.LoanDTO;
import nu.ssis.a18mosu.service.LoanService;

@RestController
public class LoanApi {

	@Autowired
	private LoanService loanService;
	
	@PostMapping("/v1/loan")
	public ResponseEntity<?> makeLoan(@Valid @RequestBody LoanDTO loan, BindingResult result) {
		if(!result.hasErrors()) {
			loanService.loanBook(loan.getRfid(), loan.getBookId());
		} else {
			List<String> errorMessages = new ArrayList<String>();
		    for (FieldError error : result.getFieldErrors()) {
		    	errorMessages.add(error.getDefaultMessage());
		    }
		    return ResponseEntity.badRequest().body(errorMessages);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/v1/loan")
	public ResponseEntity<?> returnBook(@RequestBody BookDTO bookDto) {
		if(loanService.returnBook(bookDto.getBookId())) {
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(404).build();
		}
	}
}
