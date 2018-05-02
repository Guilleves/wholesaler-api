package com.api.data;

import com.api.entities.business.User;
import com.api.data.business.ProductDataAccess;
import com.api.data.business.UserDataAccess;

public class ConsoleTest {
    private static UserDataAccess userData;
    private static ProductDataAccess productData;

    public static void main(String[] args) {
        userData = new UserDataAccess();
        productData = new ProductDataAccess();

        // *Comment para @guilleves* Acá iríamos haciendo el llamado a cada método y comentando los que van funcionando
        getUser();
        // getUsers();
        // getProduct();
        // getProducts();
        // ...etczzzzzzz

        return;
    }

    private static void getUser() {
        int userId = 2;

        User user = userData.getUser(userId);

        if (user == null)
            return;

        System.out.println(user.toString());
    }
}
