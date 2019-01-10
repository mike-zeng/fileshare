package cn.xuebao.fileshare.fileshare.controller;
import cn.xuebao.fileshare.fileshare.commen.RetJson;
import cn.xuebao.fileshare.fileshare.model.User;

import cn.xuebao.fileshare.fileshare.service.FileService;
import cn.xuebao.fileshare.fileshare.service.UserService;
import cn.xuebao.fileshare.fileshare.utils.ValidatedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    //登录接口

    @RequestMapping("/login")
    public RetJson login(User user, HttpServletRequest request){
        if (!ValidatedUtil.validate(user)){
            return RetJson.fail(-1,"参数错误");
        }
        Boolean b=userService.login(user.getUsername(),user.getPassword());
        if (b==true){
            user=userService.getUserByUserName(user.getUsername());
            request.getSession().setAttribute("uid",user.getId());
            request.getSession().setAttribute("user",user);
            return RetJson.succcess(null);
        }else {
            return RetJson.fail(-1,"登入失败");
        }
    }

    //注册接口
    @RequestMapping("/register")
    public RetJson register(User user){
        if (!ValidatedUtil.validate(user)){
            return RetJson.fail(-1,"参数错误");
        }
        if (userService.findUserByUserName(user.getUsername())!=null){
            return RetJson.fail(-1,"用户已存在");
        }
        userService.register(user);
        return RetJson.succcess(null);
    }

    @RequestMapping("/getWebCount")
    public RetJson getUserCount(){
        int count=0;
        count=userService.getUserCount();
        int fileCount=0;
        fileCount=userService.getAllFileCount();
        Map<String,Integer> map=new HashMap<>();
        map.put("userCount",count);
        map.put("fileCount",fileCount);
        return RetJson.succcess(map);
    }
}
