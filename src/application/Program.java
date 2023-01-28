package application;

import db.DB;
import repository.dao.DaoFactory;

public class Program {
    public static void main(String[] args) {

        System.out.println(DaoFactory.createSellerDao()
                .findById(3).orElse(null));

        DB.closeConnection();
    }
}
