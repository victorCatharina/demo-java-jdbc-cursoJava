package repository.dao.implementation;

import db.DB;
import db.DbException;
import repository.dao.SellerDao;
import repository.entities.Department;
import repository.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SellerDaoJDBC implements SellerDao {

    private final Connection conn;

    private final static StringBuilder BASE_CONSULT_QUERY = new StringBuilder("SELECT s.Id, s.Name, s.Email, s.BirthDate, ")
            .append("s.BaseSalary, d.Id as departmentId, d.Name as depName ")
            .append("FROM seller s ")
            .append("INNER JOIN department d ON s.DepartmentId = d.id ");


    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {


    }

    @Override
    public Optional<Seller> findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(BASE_CONSULT_QUERY.toString()
                    .concat("WHERE s.Id = ? "));

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Department dep = new Department(resultSet.getInt("departmentId"),
                        resultSet.getString("depName"));

                Seller seller = new Seller(resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Email"),
                        resultSet.getDate("BirthDate").toLocalDate(),
                        resultSet.getDouble("BaseSalary"),
                        dep);

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
    public List<Seller> findAll() {
        return null;
    }
}
