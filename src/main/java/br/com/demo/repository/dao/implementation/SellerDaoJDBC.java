package br.com.demo.repository.dao.implementation;

import br.com.demo.db.DB;
import br.com.demo.db.DbException;
import br.com.demo.repository.dao.SellerDao;
import br.com.demo.repository.entities.Department;
import br.com.demo.repository.entities.Seller;

import java.sql.Date;
import java.sql.*;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private final Connection conn;

    private final static StringBuilder BASE_QUERY = new StringBuilder("SELECT s.Id, s.Name, s.Email, s.BirthDate, ")
            .append("s.BaseSalary, d.Id as departmentId, d.Name as depName ")
            .append("FROM seller s ")
            .append("INNER JOIN department d ON s.DepartmentId = d.id ");

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Integer insert(Seller seller) {

        String insertQuery = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, Date.valueOf(seller.getBirthDate()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());

            if (statement.executeUpdate() == 0) {

                throw new DbException("Unexpected error! No rows affected!");
            }

            resultSet = statement.getGeneratedKeys();
            resultSet.next();

            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

    }

    @Override
    public void update(Seller seller) {

        String insertQuery = "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?," +
                " BaseSalary = ?, DepartmentId = ? " +
                " WHERE Id = ?";

        PreparedStatement statement = null;

        try {

            statement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, Date.valueOf(seller.getBirthDate()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());
            statement.setInt(6, seller.getId());

            if (statement.executeUpdate() == 0) {

                throw new DbException("Unexpected error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement statement = null;

        try {

            statement = conn.prepareStatement("DELETE FROM Seller WHERE Id = ?");

            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {

                throw new DbException("No rows affected!");
            }

        } catch (SQLException e) {

            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Optional<Seller> findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(BASE_QUERY.toString()
                    .concat("WHERE s.Id = ? "));

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Department dep = instantiateDepartment(resultSet);

                Seller seller = instantiateSeller(resultSet, dep);

                return Optional.of(seller);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {

            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

        return Optional.empty();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Seller> list = new ArrayList<>();
        Department dep = null;

        try {

            statement = conn.prepareStatement(BASE_QUERY.toString()
                    .concat(" WHERE s.DepartmentId = ?"));

            statement.setInt(1, department.getId());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                }

                Seller seller = instantiateSeller(resultSet, dep);

                list.add(seller);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

    }

    @Override
    public List<Seller> findAll() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Seller> list = new ArrayList<>();
        Set<Department> departments = new HashSet<>();

        try {

            statement = conn.prepareStatement(BASE_QUERY.toString());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Department department = instantiateDepartment(resultSet);

                departments.add(department);

                Seller seller = instantiateSeller(resultSet, departments.stream()
                        .filter(dep -> dep.equals(department)).findFirst().orElse(null));

                list.add(seller);
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department dep) throws SQLException {

        return new Seller(resultSet.getInt("Id"),
                resultSet.getString("Name"),
                resultSet.getString("Email"),
                resultSet.getDate("BirthDate").toLocalDate(),
                resultSet.getDouble("BaseSalary"),
                dep);
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {

        return new Department(resultSet.getInt("departmentId"),
                resultSet.getString("depName"));

    }
}
