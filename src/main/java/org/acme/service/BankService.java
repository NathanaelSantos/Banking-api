package org.acme.service;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.BankModel;
import org.acme.repository.BankRepository;

import java.util.List;

@ApplicationScoped
public class BankService {

    @Inject
    BankRepository bankRepository;

    public List<BankModel> getAllBanks() {
        return bankRepository.listAll();
    }
}
