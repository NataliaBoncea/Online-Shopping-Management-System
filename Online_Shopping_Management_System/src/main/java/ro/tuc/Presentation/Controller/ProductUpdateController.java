package ro.tuc.Presentation.Controller;

import ro.tuc.BusinessLogic.ProductBLL;
import ro.tuc.Model.Product;
import ro.tuc.Presentation.View.ProductInsertView;
import ro.tuc.Presentation.View.ProductTableGenerator;
import ro.tuc.Presentation.View.ProductUpdateView;
import ro.tuc.Presentation.View.ProductView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * @Author: Boncea Natalia
 * @Since: May 18, 2023
 */
public class ProductUpdateController {
    private ProductView productView;
    private ProductUpdateView productUpdateView;
    ProductTableGenerator reflectionProduct = new ProductTableGenerator();
    public ProductUpdateController(ProductView productView, ProductUpdateView productUpdateView){
        this.productView = productView;
        this.productUpdateView = productUpdateView;
        this.productUpdateView.updateListener(new ProductUpdateController.ProductUpdateListener(productView, productUpdateView));
    }

    class ProductUpdateListener implements ActionListener {
        private ProductView productView;
        private ProductUpdateView productUpdateView;
        private ProductBLL productBLL;
        public ProductUpdateListener(ProductView productView, ProductUpdateView productUpdateView){
            this.productView = productView;
            this.productUpdateView = productUpdateView;
            productBLL = new ProductBLL();
        }
        public void actionPerformed(ActionEvent e) {
            Product newProduct = new Product(Integer.parseInt(productUpdateView.getId()), productUpdateView.getDenumire(), Integer.parseInt(productUpdateView.getCantitate()),
                    Double.parseDouble(productUpdateView.getPret()));
            productBLL.updateProduct(newProduct);
            ArrayList<Product> productList = (ArrayList<Product>) productBLL.findAllProducts();
            reflectionProduct.setTable(productList, productView.model);
            productUpdateView.frame.dispose();
        }
    }
}
