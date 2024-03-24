package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.ClientBLL;
import ro.tuc.BusinessLogic.ProductBLL;
import ro.tuc.Model.Client;
import ro.tuc.Model.Product;
import ro.tuc.Presentation.View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ProductInsertController {
    private ProductView productView;
    private ProductInsertView productInsertView;
    ProductTableGenerator reflectionProduct = new ProductTableGenerator();
    public ProductInsertController(ProductView productView, ProductInsertView productInsertView){
        this.productView = productView;
        this.productInsertView = productInsertView;
        this.productInsertView.insertListener(new ProductInsertController.ProductInsertListener(productView, productInsertView));
    }

    class ProductInsertListener implements ActionListener {
        private ProductView productView;
        private ProductInsertView productInsertView;
        private ProductBLL productBLL;
        public ProductInsertListener(ProductView productView, ProductInsertView productInsertView){
            this.productView = productView;
            this.productInsertView = productInsertView;
            productBLL = new ProductBLL();
        }
        public void actionPerformed(ActionEvent e) {
            Product newProduct = new Product(Integer.parseInt(productInsertView.getId()), productInsertView.getDenumire(), Integer.parseInt(productInsertView.getCantitate()),
                    Double.parseDouble(productInsertView.getPret()));
            productBLL.insertProduct(newProduct);
            ArrayList<Product> productList = (ArrayList<Product>) productBLL.findAllProducts();
            reflectionProduct.setTable(productList, productView.model);
            productInsertView.frame.dispose();
        }
    }
}
