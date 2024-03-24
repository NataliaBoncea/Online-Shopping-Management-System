package ro.tuc.Model;

import java.util.Formatter;

/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class Client {
    private int id;
    private String nume;
    private String prenume;
    private String adresa;
    private String email;
    private String nr_telefon;
    public Client(){
    }

    public Client(int id, String nume, String prenume, String adresa, String email, String nrTelefon){
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.email = email;
        this.nr_telefon = nrTelefon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNr_telefon() {
        return nr_telefon;
    }

    public void setNr_telefon(String nrTelefon) {
        this.nr_telefon = nrTelefon;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", nume=" + nume + ", prenume=" + prenume + ", adresa=" + adresa + ", email=" + email + ", nr telefon=" + nr_telefon + "]";
    }
}
