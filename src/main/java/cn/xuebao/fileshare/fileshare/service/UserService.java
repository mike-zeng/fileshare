package cn.xuebao.fileshare.fileshare.service;

import cn.xuebao.fileshare.fileshare.model.User;

public interface UserService {
    public Boolean login(String userName, String password);

    public void register(User user);

    User findUserByUserName(String username);

    User getUserByUserName(String username);

    int getUserCount();

    int getAllFileCount();
}
