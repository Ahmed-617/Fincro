package tn.microfinance.fincro.dao.exceptions;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
