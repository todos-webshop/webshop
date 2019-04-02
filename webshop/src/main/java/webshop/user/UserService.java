package webshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webshop.CustomResponseStatus;
import webshop.Response;
import webshop.basket.BasketDao;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BasketDao basketDao;


    public UserService(UserDao userDao, BasketDao basketDao) {
        this.userDao = userDao;
        this.basketDao = basketDao;
    }

    public long createUserAndReturnUserId(User user) throws DuplicateKeyException {
        long newlyCreatedUserId = userDao.createUserAndReturnUserId(new User(user.getId() ,user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword(),user.getEnabled(),user.getUserRole()));
        if (newlyCreatedUserId == 0) {
            return 0;
        }
        long basketId = basketDao.createBasketForUserIdAndReturnBasketId(newlyCreatedUserId);
        if (basketId < 1) {
            throw new IllegalStateException("Basket was not created for userId: " + newlyCreatedUserId);
        }
        return newlyCreatedUserId;
    }

    public List<String> getAllUsernames() {
        return userDao.getAllUsernames();
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<User> listAllUsers() {
        return userDao.listAllUsers();
    }





    public void checkPasswordAndmodifyUser(long id, User user) {
        if (user.getPassword()==null||user.getPassword().trim().equals("")){
         userDao.modifyUserNoPassword(id,user);
       } else {
        user.setPassword(user.getPassword());
        userDao.modifyUser(id, user);
    }}








    public void modifyUserByUser(long id, User user){
        if (user.getPassword()==null||user.getPassword().trim().equals("")){
            userDao.modifyUserByUserNoPassword(id,user);
        } else {
            user.setPassword(user.getPassword());
        userDao.modifyUserByUser(id,user);
   }}


    public CustomResponseStatus logicalDeleteUserById(long id) {
        if (userDao.isAlreadyDeleted(id)) {
            return new CustomResponseStatus(Response.FAILED, "This user no longer exists.");
        }
        userDao.logicalDeleteUserById(id);
        return new CustomResponseStatus(Response.SUCCESS, "User Deleted!");
    }
    public User findUserByUserName(String userName){
        return userDao.getUserByUsername(userName);
    }

    public List<Long> listUserIds() {
        return userDao.listUserIds();
    }

}


