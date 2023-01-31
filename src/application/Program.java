package application;

import db.DB;
import repository.dao.DaoFactory;
import repository.dao.SellerDao;
import repository.entities.Department;
import repository.entities.Seller;

import java.time.LocalDate;

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

        System.out.printf("%n=== TEST 4: seller Insert ===%n");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com",
                LocalDate.of(2003, 1, 28),
                4000.00, new Department(2, null));

        System.out.println("Generated Key: " + sellerDao.insert(newSeller));


        System.out.printf("%n=== TEST 5: seller Insert ===%n");
        Seller updatedSeller = sellerDao.findById(1).orElse(null);
        assert updatedSeller != null;
        updatedSeller.setName("Martha Wai");
        sellerDao.update(updatedSeller);
        System.out.println("Update completed");

        DB.closeConnection();
    }
}
