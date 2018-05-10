package com.api.data;

import com.api.entities.business.Organization;
import com.api.entities.business.Supplier;
import com.api.data.business.OrganizationDataAccess;
import java.util.ArrayList;

import com.api.entities.business.Category;
import com.api.entities.business.Brand;
import com.api.entities.business.Product;
import com.api.entities.business.User;
import com.api.data.business.ProductDataAccess;
import com.api.data.business.UserDataAccess;

public class ConsoleTest {
    private static UserDataAccess userData;
    private static ProductDataAccess productData;
    private static OrganizationDataAccess organizationData;

    public static void main(String[] args) {
        userData = new UserDataAccess();
        productData = new ProductDataAccess();
        organizationData = new OrganizationDataAccess();

        // *Comment para @guilleves* Acá iríamos haciendo el llamado a cada método y comentando los que van funcionando
        // getUser();
        // getUsers();

        // #region Products

        // saveProduct();
        // updateProduct();
        // getProduct();
        // getProducts();

        // #region Supplier

        saveSupplier();
        // updateProduct();
        // getProduct();
        // getProducts();

        // #endregion

        return;
    }

    // region Supplier
    private static void saveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Longvie");
        supplier.setLegalName("Longvie S.A.");
        supplier.setCuit("2342323424");

        supplier = (Supplier) organizationData.createOrganization((Organization) supplier);

        if (supplier.getId() == 0)
            System.out.println("Supplier could not be created");
        else
            System.out.println(supplier.getName() + " " + supplier.getId());
    }

    // #region Products

    private static void saveProduct() {
        Product product = new Product(
            0,
            "Router",
            "0142152332145",
            new Brand(1, "TP-Link"),
            new Category(1, "PC Components")
        );

        int id = productData.createProduct(product);

        if (id == 0)
            System.out.println("Product could not be created");
        else
            System.out.println(id);
    }

    private static void updateProduct() {
        Product product = productData.getProduct(1);

        product.setName("El rútero muy grande");

        int id = productData.updateProduct(product);

        if (id == 0)
            System.out.println("Product could not be updated");
        else
            System.out.println(id);
    }

    private static void getProduct() {
        int productId = 1;

        Product product = productData.getProduct(productId);

        if (product == null) {
            System.out.println("Couldn't find a product.");
            return;
        }

        System.out.println(product.toString());
    }

    private static void getProducts() {
        ArrayList<Product> products = productData.getProducts();

        for(Product product : products) {
            System.out.println(product.toString());
        }

        System.out.println("End of product list.");
    }

    // #endregion

    private static void getUser() {
        int userId = 2;

        User user = userData.getUser(userId);

        if (user == null)
            return;

        System.out.println(user.toString());
    }
}
