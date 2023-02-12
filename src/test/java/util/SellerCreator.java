package util;

import br.com.demo.repository.entities.Department;
import br.com.demo.repository.entities.Seller;

import java.time.LocalDate;

public class SellerCreator {

    public static Seller insertSeller() {

        return new Seller(null, "Test", "test@gmail.com",
                LocalDate.of(1999, 1, 31),
                3000.00, new Department(1, null));
    }

    public static Seller updateSeller() {

        return new Seller(1, "Test update", "test@gmail.com",
                LocalDate.of(1999, 1, 31),
                3000.00, new Department(1, null));
    }

}
