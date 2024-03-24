package ro.tuc.BusinessLogic.Validators;

import ro.tuc.Model.Product;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */

public class QuantityValidator implements Validator<Product>{
    private static final int MIN_QUANTITY = 1;

    public void validate(Product t) {

        if (t.getCantitate() < MIN_QUANTITY) {
            throw new IllegalArgumentException("Cantitatea minima nu este respectata!");
        }

    }
}
