package br.com.manager.repository;


import br.com.manager.model.Client;

public class ClientRepository extends GenericRepository<Client, Long> {

    public ClientRepository() {
        super(Client.class);
    }

}
