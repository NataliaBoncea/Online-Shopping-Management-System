package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.Model.Client;
import ro.tuc.Presentation.View.ClientTableGenerator;
import ro.tuc.Presentation.View.ClientUpdateView;
import ro.tuc.Presentation.View.ClientView;
import ro.tuc.Presentation.View.ErrorBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ClientUpdateController {
    private ClientView clientView;
    private ClientUpdateView clientUpdateView;
    ClientTableGenerator reflectionClient = new ClientTableGenerator();
    public ClientUpdateController(ClientView clientView, ClientUpdateView clientUpdateView){
        this.clientView = clientView;
        this.clientUpdateView = clientUpdateView;
        this.clientUpdateView.updateListener(new ClientUpdateListener(clientView, clientUpdateView));
    }

    class ClientUpdateListener implements ActionListener {
        protected ClientView clientView;
        private ClientUpdateView clientUpdateView;
        private ClientBLL clientBLL;
        public ClientUpdateListener(ClientView clientView, ClientUpdateView clientUpdateView){
            this.clientView = clientView;
            this.clientUpdateView = clientUpdateView;
            clientBLL = new ClientBLL();
        }
        public void actionPerformed(ActionEvent e) {
            verifField(clientUpdateView.getIdT(), clientUpdateView.getId());
            verifField(clientUpdateView.getNumeT(), clientUpdateView.getNume());
            verifField(clientUpdateView.getPrenumeT(), clientUpdateView.getPrenume());
            verifField(clientUpdateView.getAdresaT(), clientUpdateView.getAdresa());
            verifField(clientUpdateView.getEmailT(), clientUpdateView.getEmail());
            verifField(clientUpdateView.getTelefonT(), clientUpdateView.getTelefon());

            Client newClient = new Client(Integer.parseInt(clientUpdateView.getId()), clientUpdateView.getNume(), clientUpdateView.getPrenume(),
                    clientUpdateView.getAdresa(), clientUpdateView.getEmail(), clientUpdateView.getTelefon());

            try {
                clientBLL.updateClient(newClient);
                ArrayList<Client> clientList = (ArrayList<Client>) clientBLL.findAllClients();
                reflectionClient.setTable(clientList, clientView.model);
                clientUpdateView.frame.dispose();
            }catch (IllegalArgumentException ex){
                if(ex.getMessage().equals("Email invalid!")){
                    ErrorBox.incorrectValueError(clientUpdateView.getEmailT(), ex.getMessage(), clientUpdateView);
                }
                if(ex.getMessage().equals("Numar de telefon invalid!")){
                    ErrorBox.incorrectValueError(clientUpdateView.getTelefonT(), ex.getMessage(), clientUpdateView);
                }
            }
            ArrayList<Client> clientList = (ArrayList<Client>) clientBLL.findAllClients();
            reflectionClient.setTable(clientList, clientView.model);
            clientUpdateView.frame.dispose();
        }

        public void verifField(JTextField tf, String s){
            if(s.equals("")){
                ErrorBox.incorrectValueError(tf, "Camp necompletat!", clientUpdateView);
                throw new IllegalArgumentException("Camp necompletat!");
            }
            ErrorBox.correctValue(tf, null, clientUpdateView);
        }
    }
}
