package br.com.demo.repository.dao;

import br.com.demo.repository.entities.Department;
import br.com.demo.repository.entities.Seller;

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
