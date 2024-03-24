package ro.tuc.Start;

import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.BusinessLogic.Reflection;
import ro.tuc.Model.Client;
import ro.tuc.Presentation.Controller.Controller;
import ro.tuc.Presentation.View.ClientView;
import ro.tuc.Presentation.View.View;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class Start {

    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
    public static void main(String[] args){
        View theView = new View();
        Controller controller = new Controller(theView);
        Client client = new Client (2, "Pop", "Cosmin", "Republicii 3", "popcosmin@gmail.com", "0789892345");
        ClientBLL clientBll = new ClientBLL();
//        int id = clientBll.insertClient(client);
//        if (id > 0) {
//            clientBll.findClientById(id);
//        }
        clientBll.findClientById(2);

        // Generate error
        try {
            clientBll.findClientById(2);
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }


    //obtain field-value pairs for object through reflection
		Reflection.retrieveProperties(client);
    }
}