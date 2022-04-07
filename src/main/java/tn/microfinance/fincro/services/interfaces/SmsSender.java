package tn.microfinance.fincro.services.interfaces;

import tn.microfinance.fincro.dao.model.SmsRequest;

public interface SmsSender {
    void sendSms(SmsRequest smsRequest);
}
