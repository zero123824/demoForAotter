package idv.jmproject.usercenter.service.impl;

import idv.jmproject.usercenter.model.User;
import idv.jmproject.usercenter.model.repository.UserRepository;
import idv.jmproject.usercenter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    /**
     *  存入DB時再次檢測userName是否已經存在db內，若已存在則userName + 數字
     * @param inputUser
     * @return
     */
    @Override
    public User registerUser(User inputUser) {
        Long counter = new Long(1);
        String oriUserName = inputUser.getUserName();
        while(true){
            if(checkUserIsExisted(inputUser.getUserName())){
                logger.info("user is already existed in DB");
                inputUser.setUserName(oriUserName + counter++);
            }else{
                break;
            }
        }
        User returnUser = userRepository.save(inputUser);
        return returnUser;
    }

    private boolean checkUserIsExisted(String userName){
        if(userRepository.findByUserName(userName).size() != 0){
            return true;
        }else{
            return false;
        }
    }
}
