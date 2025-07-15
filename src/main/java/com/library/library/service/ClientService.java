package com.library.library.service;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.library.library.model.Client;
import com.library.library.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public Client salvar(Client client) {
        return repository.save(client);
    }

    public Optional<Client> obterClientID(String id) {
        return repository.findById(UUID.fromString(id));
    }
}
