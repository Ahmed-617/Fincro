package tn.microfinance.fincro.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.SmsRequest;
import tn.microfinance.fincro.services.interfaces.SmsSender;

@Service
@AllArgsConstructor
public class SmsService {

    private final SmsSender smsSender;

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }
}//

