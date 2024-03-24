package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.Model.Client;
import ro.tuc.Presentation.View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ClientController {
    private ClientView clientView;
    ClientTableGenerator reflectionClient = new ClientTableGenerator();
    public ClientController(ClientView clientView){
        this.clientView = clientView;
        this.clientView.searchListener(new SearchListener(clientView));
        this.clientView.deleteListener(new DeleteListener(clientView));
        this.clientView.updateListener(new UpdateListener(clientView));
        this.clientView.insertListener(new InsertListener(clientView));
    }

    class DeleteListener implements ActionListener {
        private ClientView clientView;
        private ClientBLL clientBLL;
        public DeleteListener(ClientView clientView){
            this.clientView = clientView;
            clientBLL = new ClientBLL();
        }
        public void actionPerformed(ActionEvent e) {
            int id;
            try{
                id = clientView.getSelectedId();
            }catch(ArrayIndexOutOfBoundsException ex){
                ErrorBox.unselectedOption("Clientul nu a fost selectat!", clientView);
                throw new ArrayIndexOutOfBoundsException("Clientul nu a fost selectat!");
            }
            clientBLL.deleteClient(id);
            ArrayList<Client> clientList = (ArrayList<Client>) clientBLL.findAllClients();
            reflectionClient.setTable(clientList, clientView.model);
        }
    }

    class InsertListener implements ActionListener {
        private ClientView clientView;
        private ClientInsertView insertView;
        private ClientInsertController clientInsertController;
        public InsertListener(ClientView clientView){
            this.clientView = clientView;
        }

        public void actionPerformed(ActionEvent e) {
            insertView = new ClientInsertView(clientView);
            clientInsertController = new ClientInsertController(clientView, insertView);

        }
    }

    class SearchListener implements ActionListener {
        private ClientView clientView;
        private ClientBLL clientBLL;
        private String searchList[] = {"id", "nume", "prenume", "adresa", "afiseaza tot"};
        public SearchListener(ClientView clientView){
            this.clientView = clientView;
            clientBLL = new ClientBLL();
        }
        public void actionPerformed(ActionEvent e) {
            int index = clientView.getSearchOption();
            ArrayList<Client> clientList;
            if(index == 4){
                clientList = (ArrayList<Client>) clientBLL.findAllClients();
                ErrorBox.correctValue(clientView.getSearchText(), null, clientView);
            }
            else if(index == 0){
                clientList = new ArrayList<>();
                if(clientView.getSearch().equals("")){
                    ErrorBox.incorrectValueError(clientView.getSearchText(), "Camp necompletat!", clientView);
                    throw new IllegalArgumentException("Camp necompletat!");
                }
                ErrorBox.correctValue(clientView.getSearchText(), null, clientView);
                clientList.add(clientBLL.findClientById(Integer.parseInt(clientView.getSearch())));
            }
            else{
                if(clientView.getSearch().equals("")){
                    ErrorBox.incorrectValueError(clientView.getSearchText(), "Camp necompletat!", clientView);
                    throw new IllegalArgumentException("Camp necompletat!");
                }
                ErrorBox.correctValue(clientView.getSearchText(), null, clientView);
                clientList = (ArrayList<Client>) clientBLL.findClientsByString(searchList[index], clientView.getSearch());
            }
            reflectionClient.setTable(clientList, clientView.model);
        }
    }

    class UpdateListener implements ActionListener {
        private ClientView clientView;
        private ClientUpdateView updateView;
        private ClientUpdateController clientUpdateController;
        public UpdateListener(ClientView clientView){
            this.clientView = clientView;
        }

        public void actionPerformed(ActionEvent e) {
            try{
                clientView.getSelectedId();
            }catch(ArrayIndexOutOfBoundsException ex){
                ErrorBox.unselectedOption("Clientul nu a fost selectat!", clientView);
                throw new ArrayIndexOutOfBoundsException("Clientul nu a fost selectat!");
            }
            updateView = new ClientUpdateView(clientView);
            clientUpdateController = new ClientUpdateController(clientView, updateView);

        }
    }
}
