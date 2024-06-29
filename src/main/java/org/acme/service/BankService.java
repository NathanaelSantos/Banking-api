package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.Exception.bank.NoBanksFoundException;
import org.acme.Exception.service.BankServiceException;
import org.acme.model.BankModel;
import org.acme.repository.BankRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class BankService {

    private static final Logger log = Logger.getLogger(BankService.class.getName());

    @Inject
    BankRepository bankRepository;

    public List<BankModel> getAllBanks() {
        try {
            List<BankModel>  banks = bankRepository.listAll();
            if (banks.isEmpty()) {
                log.warning("No banks found in the database");
                throw new NoBanksFoundException();
            }
            return banks;
        }catch (Exception e){
            log.log(Level.SEVERE, "Error getting all banks", e);
            throw new BankServiceException("An error occurred while getting all banks", e);
        }
    }
}
