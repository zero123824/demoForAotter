package idv.jmproject.usercenter.controller;

import com.alibaba.fastjson.JSON;
import idv.jmproject.usercenter.model.User;
import idv.jmproject.usercenter.service.RedisService;
import idv.jmproject.usercenter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {
    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    /**
     *  註冊會員時，在redis cache裡，將username當鎖的key值存入，以保證當前只有一位會員能夠註冊唯一的userName
     * @param user
     * @return
     */
    @PostMapping("/user")
    public String register(@RequestBody User user){
        Long counter = new Long(1);
        String oriUserName = user.getUserName();
        while(true){
            if(checkUserNameIsInRedis(user.getUserName(), counter)){
                user.setUserName(oriUserName + counter++);
            }else{
                break;
            }
        }

        User retUser = userService.registerUser(user);
        return JSON.toJSONString(retUser);
    }

    private boolean checkUserNameIsInRedis(String userName, Long counter){
        Long lockedValue = redisService.checkKey(userName, counter);
        if(lockedValue.equals(counter)){
            logger.info("locking userName failed, key is existed");
            counter = lockedValue;
            return true;
        }else{
            redisService.setKey(userName, counter);
            return false;
        }
    }
}