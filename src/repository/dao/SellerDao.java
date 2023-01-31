package repository.dao;

import repository.entities.Department;
import repository.entities.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerDao {

    Integer insert(Seller seller);

    void update(Seller seller);

    void deleteById(Integer id);

    Optional<Seller> findById(Integer id);

    List<Seller> findByDepartment(Department department);

    List<Seller> findAll();
}
