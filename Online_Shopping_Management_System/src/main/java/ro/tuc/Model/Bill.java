package ro.tuc.Model;

import ro.tuc.BusinessLogic.ProductBLL;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public record Bill(int id, String numeClient, String adresa, String telefon, String produs, int cantitate, double pret, double pret_total, FileWriter logFile)
{

    public void writeBill(ArrayList<Bill> billList){
        try {
            //logFile = new FileWriter("SimulationLog.txt");
            ProductBLL productBLL = new ProductBLL();
            logFile.write("Factura\n");
            logFile.write("--------------------------------------------------------------------------------------------------------------------\n");
            logFile.write("Destinatar: " + numeClient + "\n");
            logFile.write("Adresa: " + adresa + "\n");
            logFile.write("Telefon: " + telefon + "\n");
            logFile.write("--------------------------------------------------------------------------------------------------------------------\n");
            for(Bill b: billList){
                logFile.write(b.produs + "  x  " + b.cantitate + "         " + b.pret + "\n");
            }
            logFile.write("--------------------------------------------------------------------------------------------------------------------\n");
            logFile.write("Pret total: " + pret_total + "\n");
            logFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
};
