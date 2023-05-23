package lk.ijse.system.service.custom;

import lk.ijse.system.dto.UserDTO;
import lk.ijse.system.service.SuperService;

import java.sql.SQLException;

public interface UserService extends SuperService {
    public UserDTO search(String code) throws SQLException, ClassNotFoundException;

    public boolean save (UserDTO userDTO) throws SQLException, ClassNotFoundException;

    public boolean update(UserDTO userDTO) throws SQLException, ClassNotFoundException;
}
