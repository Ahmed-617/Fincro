package tn.microfinance.fincro.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.microfinance.fincro.dao.model.Transaction;
import tn.microfinance.fincro.dao.repositories.TransactionRepository;
import tn.microfinance.fincro.services.interfaces.TransactionService;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> retrieveAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    @Override
    public Transaction addTransaction(Transaction t) {
        return transactionRepository.save(t);
    }

    @Override
    public void deleteTransaction(Integer id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Transaction retrieveTransaction(Integer id) {
        return transactionRepository.findById(id).get();
    }
}
