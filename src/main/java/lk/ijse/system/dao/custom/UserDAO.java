package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.SuperDAO;
import lk.ijse.system.entity.User;

import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
    public boolean updateuser(User u1) throws SQLException, ClassNotFoundException;

    public  boolean save(User u1) throws SQLException, ClassNotFoundException;

    public  User search(String code) throws SQLException, ClassNotFoundException;
}
