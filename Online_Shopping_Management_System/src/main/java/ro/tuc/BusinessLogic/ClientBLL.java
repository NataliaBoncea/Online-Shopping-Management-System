package ro.tuc.BusinessLogic;

import ro.tuc.BusinessLogic.Validators.EmailValidator;
import ro.tuc.BusinessLogic.Validators.TelephoneValidator;
import ro.tuc.BusinessLogic.Validators.Validator;
import ro.tuc.Model.Client;
import ro.tuc.DataAccess.ClientDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    public ClientBLL(){
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new TelephoneValidator());
    }

    public List<Client> findAllClients() {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clientsList = clientDAO.findAll();
        return clientsList;
    }
    public List<Client> findClientsByInt(String field, int cond) {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clientsList = clientDAO.findByInt(field, cond);
        return clientsList;
    }
    public List<Client> findClientsByString(String field, String cond) {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> clientsList = clientDAO.findByString(field, cond);
        return clientsList;
    }
    public Client findClientById(int id) {
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("Clientul cu id =" + id + " nu a fost gasit!");
        }
        return client;
    }

    public int insertClient(Client client) throws IllegalArgumentException{
        ClientDAO clientDAO = new ClientDAO();
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return clientDAO.insert(client);
    }

    public Client updateClient(Client client) {
        ClientDAO clientDAO = new ClientDAO();
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return clientDAO.update(client);
    }

    public void deleteClient(int id){
        ClientDAO clientDAO = new ClientDAO();
        clientDAO.delete(id);
    }
}
