package tn.microfinance.fincro.dao.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString

public class SmsRequest {
    private final String phoneNumber;
    private final String message;

}
