package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.BankModel;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class BankRepository implements PanacheRepository<BankModel> { }
