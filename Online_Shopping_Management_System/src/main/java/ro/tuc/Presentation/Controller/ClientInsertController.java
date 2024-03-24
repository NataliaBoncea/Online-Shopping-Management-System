package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.Model.Client;
import ro.tuc.Presentation.View.ClientInsertView;
import ro.tuc.Presentation.View.ClientTableGenerator;
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
public class ClientInsertController {
    private ClientView clientView;
    private ClientInsertView clientInsertView;
    ClientTableGenerator reflectionClient = new ClientTableGenerator();
    public ClientInsertController(ClientView clientView, ClientInsertView clientInsertView){
        this.clientView = clientView;
        this.clientInsertView = clientInsertView;
        this.clientInsertView.updateListener(new ClientInsertListener(clientView, clientInsertView));
    }

    class ClientInsertListener implements ActionListener {
        protected ClientView clientView;
        private ClientInsertView clientInsertView;
        private ClientBLL clientBLL;
        public ClientInsertListener(ClientView clientView, ClientInsertView clientInsertView){
            this.clientView = clientView;
            this.clientInsertView = clientInsertView;
            clientBLL = new ClientBLL();
        }
        public void actionPerformed(ActionEvent e) {
            verifField(clientInsertView.getIdT(), clientInsertView.getId());
            verifField(clientInsertView.getNumeT(), clientInsertView.getNume());
            verifField(clientInsertView.getPrenumeT(), clientInsertView.getPrenume());
            verifField(clientInsertView.getAdresaT(), clientInsertView.getAdresa());
            verifField(clientInsertView.getEmailT(), clientInsertView.getEmail());
            verifField(clientInsertView.getTelefonT(), clientInsertView.getTelefon());
            Client newClient = new Client(Integer.parseInt(clientInsertView.getId()), clientInsertView.getNume(), clientInsertView.getPrenume(),
                    clientInsertView.getAdresa(), clientInsertView.getEmail(), clientInsertView.getTelefon());
            try {
                clientBLL.insertClient(newClient);
                ArrayList<Client> clientList = (ArrayList<Client>) clientBLL.findAllClients();
                reflectionClient.setTable(clientList, clientView.model);
                clientInsertView.frame.dispose();
            }catch (IllegalArgumentException ex){
                if(ex.getMessage().equals("Email invalid!")){
                    ErrorBox.incorrectValueError(clientInsertView.getEmailT(), ex.getMessage(), clientInsertView);
                }
                if(ex.getMessage().equals("Numar de telefon invalid!")){
                    ErrorBox.incorrectValueError(clientInsertView.getTelefonT(), ex.getMessage(), clientInsertView);
                }
            }
        }

        public void verifField(JTextField tf, String s){
            if(s.equals("")){
                ErrorBox.incorrectValueError(tf, "Camp necompletat!", clientInsertView);
                throw new IllegalArgumentException("Camp necompletat!");
            }
            ErrorBox.correctValue(tf, null, clientInsertView);
        }
    }
}
