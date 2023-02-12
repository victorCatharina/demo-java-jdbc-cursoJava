package util;

import br.com.demo.repository.entities.Department;

public class DepartmentCreator {

    public static Department insertDepartment() {

        return new Department(null, "Department Test");
    }

    public static Department updateDepartment() {

        return new Department(1, "Department Test - update");
    }

}
