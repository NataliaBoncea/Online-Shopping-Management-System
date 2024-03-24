package ro.tuc.Model;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class Product {

    private int id;
    private String denumire;
    private double pret;
    private int cantitate;

    public Product(){}
    public Product(int id, String denumire, int cantitate, double pret){
        this.id = id;
        this.cantitate = cantitate;
        this.pret = pret;
        this.denumire = denumire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Produs [id=" + id + ", denumire=" + denumire + ", pret=" + pret + ", cantitate=" + cantitate + "]";
    }
}
