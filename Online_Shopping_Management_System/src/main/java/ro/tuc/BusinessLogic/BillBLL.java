package ro.tuc.BusinessLogic;

import ro.tuc.BusinessLogic.Validators.Validator;
import ro.tuc.DataAccess.BillDAO;
import ro.tuc.DataAccess.ClientDAO;
import ro.tuc.DataAccess.OrderDAO;
import ro.tuc.Model.Bill;
import ro.tuc.Model.Client;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class BillBLL {
    public ArrayList<Bill> findAllBills() {
        BillDAO billDAO = new BillDAO();
        ArrayList<Bill> billList = billDAO.findAll();
        return billList;
    }

    public int insertBill(Bill bill) {
        BillDAO billDAO = new BillDAO();
        return billDAO.insert(bill);
    }

    public static  int count(){
        return BillDAO.count();
    }
}
