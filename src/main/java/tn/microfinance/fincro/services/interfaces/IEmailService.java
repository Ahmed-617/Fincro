package tn.microfinance.fincro.services.interfaces;

public interface IEmailService {
    public void sendEmailAmountReceived(String email,Double amount);
    public void sendEmailRefuseCase(String email);
}
