package repository.dao;

import repository.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {

    void insert(Department department);

    void update(Department department);

    void deleteById(Integer id);

    Optional<Department> findById(Integer id);

    List<Department> findAll();
}
