package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.ItemDAO;
import lk.ijse.system.dao.custom.UserDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.UserDTO;
import lk.ijse.system.entity.User;
import lk.ijse.system.service.custom.UserService;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final Converter converter;
    private final Connection connection;
    private final UserDAO userDAO;

    public UserServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        userDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.USER);
    }

    @Override
    public UserDTO search(String code) throws SQLException, ClassNotFoundException {
        User search = userDAO.search(code);
        return converter.fromUser(search);
    }

    @Override
    public boolean save(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        boolean save = userDAO.save(converter.toUser(userDTO));
        return save;
    }

    @Override
    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        boolean updateuser = userDAO.updateuser(converter.toUser(userDTO));
        return updateuser;
    }
}
