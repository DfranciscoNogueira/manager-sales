package br.com.manager.service;

import br.com.manager.dto.ClientDTO;
import br.com.manager.model.Client;
import br.com.manager.repository.ClientRepository;
import br.com.manager.util.TextManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepository();
    }

    public void save(ClientDTO dto) {

        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException(TextManager.getLabel("client.null"));
        }

        this.clientRepository.save(dto.toModel());

    }

    public Optional<ClientDTO> findById(Long id) {

        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(TextManager.getLabel("client.null.id"));
        }

        return this.clientRepository.findById(id).map(Client::toDTO);
    }

    public List<ClientDTO> findAll() {
        return this.clientRepository.findAll().stream().map(Client::toDTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        this.clientRepository.delete(id);
    }

}
