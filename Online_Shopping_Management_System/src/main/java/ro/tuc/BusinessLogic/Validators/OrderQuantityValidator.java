package ro.tuc.BusinessLogic.Validators;

import ro.tuc.DataAccess.ProductDAO;
import ro.tuc.Model.Orders;
import ro.tuc.Model.Product;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */

public class OrderQuantityValidator implements Validator<Orders>{
    private static final int MIN_QUANTITY = 1;
    public void validate(Orders t) throws IllegalArgumentException{
        ProductDAO productDAO  = new ProductDAO();
        Product product = productDAO.findById(t.getId_produs());
        if(t.getCantitate() > product.getCantitate()){
            throw new IllegalArgumentException("Cantitatea insuficienta pe stoc!");
        }
        if (t.getCantitate() < MIN_QUANTITY) {
            throw new IllegalArgumentException("Cantitatea minima nu este respectata!");
        }
    }
}
