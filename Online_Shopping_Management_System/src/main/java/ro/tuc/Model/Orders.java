package ro.tuc.Model;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class Orders {
    private int id;
    private int id_client;
    private int id_produs;
    private int cantitate;
    private double pret_total;

    public Orders(){}
    public Orders(int id, int id_client, int id_produs, int cantitate, double pret_total){
        this.id = id;
        this.id_client = id_client;
        this.id_produs = id_produs;
        this.cantitate = cantitate;
        this.pret_total = pret_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_produs() {
        return id_produs;
    }

    public void setId_produs(int id_produs) {
        this.id_produs = id_produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public double getPret_total() {
        return pret_total;
    }

    public void setPret_total(double pret_total) {
        this.pret_total = pret_total;
    }

    @Override
    public String toString() {
        return "Client [id comanda=" + id + ", id client=" + id_client + ", id produs=" + id_produs + ", cantitate=" + cantitate + ", pret=" + pret_total + "]";
    }
}
