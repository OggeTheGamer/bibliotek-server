package nu.ssis.a18mosu.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends NoSuchElementException {

	private static final long serialVersionUID = -7053834581688918244L;
	
    public NotFoundException() {
        super();
    }
    public NotFoundException(String message) {
        super(message);
    }

}
