package ro.tuc.BusinessLogic;

import ro.tuc.BusinessLogic.Validators.OrderQuantityValidator;
import ro.tuc.BusinessLogic.Validators.QuantityValidator;
import ro.tuc.BusinessLogic.Validators.Validator;
import ro.tuc.DataAccess.ClientDAO;
import ro.tuc.DataAccess.OrderDAO;
import ro.tuc.DataAccess.ProductDAO;
import ro.tuc.Model.Client;
import ro.tuc.Model.Orders;
import ro.tuc.Model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * @Author: Boncea Natalia
 *
 * @Since: May 18, 2023
 */
public class ProductBLL {
    private List<Validator<Product>> validators;
    public ProductBLL(){
        validators = new ArrayList<Validator<Product>>();
        validators.add(new QuantityValidator());
    }

    public List<Product> findAllProducts() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> productsList = productDAO.findAll();
        return productsList;
    }
    public Product findProductById(int id) {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("Produsul cu id =" + id + " nu a fost gasit!");
        }
        return product;
    }
    public List<Product> findProductsByInt(String field, int cond) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> productsList = productDAO.findByInt(field, cond);
        return productsList;
    }
    public List<Product> findProductsByString(String field, String cond) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> productsList = productDAO.findByString(field, cond);
        return productsList;
    }
    public int insertProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return productDAO.insert(product);
    }

    public Product updateProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return productDAO.update(product);
    }
    public void deleteProduct(int id){
        ProductDAO productDAO = new ProductDAO();
        productDAO.delete(id);
    }

    public List<Product> findProductsByMinPrice(int cond){
        ProductDAO productDAO = new ProductDAO();
        return productDAO.findProductsByMinPrice(cond);
    }
    public List<Product> findProductsByMaxPrice(int cond){
        ProductDAO productDAO = new ProductDAO();
        return productDAO.findProductsByMaxPrice(cond);
    }
}
