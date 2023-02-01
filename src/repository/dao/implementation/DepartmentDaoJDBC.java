package repository.dao.implementation;

import db.DB;
import db.DbException;
import repository.dao.DepartmentDao;
import repository.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection conn;

    private final static String BASE_QUERY = "SELECT d.Id, d.Name FROM department d";

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {

    }

    @Override
    public void update(Department department) {

    }

    @Override
    public void deleteById(Integer id) {

        String deleteQuery = "DELETE FROM department WHERE Id = ?";
        PreparedStatement statement = null;

        try {

            statement = conn.prepareStatement(deleteQuery);
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DbException("Unexpected erro! No rows affected");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Optional<Department> findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            statement = conn.prepareStatement(BASE_QUERY.concat(" WHERE d.Id = ?"));
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return Optional.of(instantiateDepartment(resultSet));
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
    public List<Department> findAll() {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Department> departments = new ArrayList<>();

        try {

            statement = conn.prepareStatement(BASE_QUERY);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                departments.add(instantiateDepartment(resultSet));
            }

            return departments;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {

            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {

        return new Department(resultSet.getInt("departmentId"),
                resultSet.getString("depName"));

    }
}
