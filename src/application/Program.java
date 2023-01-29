package application;

import db.DB;
import repository.dao.DaoFactory;
import repository.dao.SellerDao;
import repository.entities.Department;

public class Program {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.printf("%n=== TEST 1: seller findById ===%n");
        System.out.println(sellerDao.findById(3).orElse(null));

        System.out.printf("%n=== TEST 2: seller findByDepartment ===%n");
        sellerDao.findByDepartment(new Department(2, null))
                .forEach(System.out::println);

        System.out.printf("%n=== TEST 3: seller findAll ===%n");
        sellerDao.findAll().forEach(System.out::println);

        DB.closeConnection();
    }
}
