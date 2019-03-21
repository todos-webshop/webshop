package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    public int createUser(User user) {
        return userDao.createUser(user);
    }

    public List<String> getAllUsernames() {
        return userDao.getAllUsernames();
    }
    public List<User> listAllUsers(){return userDao.listAllUsers();}
}

