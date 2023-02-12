package br.com.demo.repository.dao;

import br.com.demo.db.DB;
import br.com.demo.repository.dao.implementation.DepartmentDaoJDBC;
import br.com.demo.repository.dao.implementation.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }

}
