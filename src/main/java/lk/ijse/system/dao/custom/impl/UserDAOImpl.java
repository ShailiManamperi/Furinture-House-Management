package lk.ijse.system.dao.custom.impl;

import lk.ijse.system.dao.custom.UserDAO;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean updateuser(User u1) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET verification = ? WHERE user_name = ?";
        return DBUtil.executeUpdate(sql,
                u1.getVerification(),
                u1.getUsername()
        );
    }

    @Override
    public boolean save(User u1) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user VALUES (?, ? ,?, ?)";
        return DBUtil.executeUpdate(sql,
                u1.getUsername(),
                u1.getType(),
                u1.getPassword(),
                u1.getVerification()
        );
    }

    @Override
    public User search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM user WHERE user_name = ?";
        ResultSet result = DBUtil.executeQuery(sql, code);

        if (result.next()) {
            return new User(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
}
