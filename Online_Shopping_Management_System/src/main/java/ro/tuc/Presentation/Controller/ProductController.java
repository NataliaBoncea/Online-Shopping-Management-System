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
public class ProductController {
    private ProductView productView;
    ProductTableGenerator reflectionProduct = new ProductTableGenerator();
    public ProductController(ProductView productView){
        this.productView = productView;
        this.productView.searchListener(new ProductController.SearchListener(productView));
        this.productView.deleteListener(new ProductController.DeleteListener(productView));
        this.productView.updateListener(new ProductController.UpdateListener(productView));
        this.productView.insertListener(new ProductController.InsertListener(productView));
    }

    class DeleteListener implements ActionListener {
        private ProductView productView;
        private ProductBLL productBLL;
        public DeleteListener(ProductView productView){
            this.productView = productView;
            productBLL = new ProductBLL();
        }
        public void actionPerformed(ActionEvent e) {
            int id = productView.getSelectedId();
            productBLL.deleteProduct(id);
            ArrayList<Product> productList = (ArrayList<Product>) productBLL.findAllProducts();
            reflectionProduct.setTable(productList, productView.model);
        }
    }

    class InsertListener implements ActionListener {
        private ProductView productView;
        private ProductInsertView insertView;
        private ProductInsertController productInsertController;
        public InsertListener(ProductView productView){
            this.productView = productView;
        }

        public void actionPerformed(ActionEvent e) {
            insertView = new ProductInsertView(productView);
            productInsertController = new ProductInsertController(productView, insertView);

        }
    }

    class SearchListener implements ActionListener {
        private ProductView productView;
        private ProductBLL productBLL;
        private String searchList[] = {"ID", "denumire", "cantitate", "pret minim", "pret maxim", "afiseaza tot"};
        public SearchListener(ProductView productView){
            this.productView = productView;
            productBLL = new ProductBLL();
        }
        public void actionPerformed(ActionEvent e) {
            int index = productView.getSearchOption();
            ArrayList<Product> productList;
            switch(index){
                case 0:
                    productList = new ArrayList<>();
                    productList.add(productBLL.findProductById(Integer.parseInt(productView.getSearch())));
                    break;
                case 1: productList = (ArrayList<Product>) productBLL.findProductsByString(searchList[index], productView.getSearch());
                    break;
                case 2: productList = (ArrayList<Product>) productBLL.findProductsByInt(searchList[index], Integer.parseInt(productView.getSearch()));
                    break;
                case 3: productList = (ArrayList<Product>) productBLL.findProductsByMinPrice(Integer.parseInt(productView.getSearch()));
                    break;
                case 4: productList = (ArrayList<Product>) productBLL.findProductsByMaxPrice(Integer.parseInt(productView.getSearch()));
                    break;
                case 5: productList = (ArrayList<Product>) productBLL.findAllProducts();
                    break;
                default: productList = new ArrayList<>();
            }
            reflectionProduct.setTable(productList, productView.model);
        }
    }

    class UpdateListener implements ActionListener {
        private ProductView productView;
        private ProductUpdateView updateView;
        private ProductUpdateController productUpdateController;
        public UpdateListener(ProductView productView){
            this.productView = productView;
        }

        public void actionPerformed(ActionEvent e) {
            updateView = new ProductUpdateView(productView);
            productUpdateController = new ProductUpdateController(productView, updateView);

        }
    }
}
