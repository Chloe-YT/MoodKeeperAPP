package edu.fzu.moodkeeper.service;

import edu.fzu.moodkeeper.service.model.UserModel;
import edu.fzu.moodkeeper.error.BusinessException;

/* 设计具体实现方法之前，先抽象为接口，是一种良好设计的表现 */
public interface UserService {
    /*
    返回值应该提供应有的所有属性
     而整合业务需求就是model层的工作
     因此返回值类型应该是model类型
     */
    public UserModel getUserById(Integer id);

    /* 用户获取手机验证码时需要用到 */
    public Boolean getUserByTelephone(String telephone);

    public UserModel getUserByTelephone1(String telephone);

    void register(UserModel userModel) throws BusinessException;

    void updateMessage(UserModel userModel) throws BusinessException;

    void findPassword(UserModel userModel) throws BusinessException;

    UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException;

    void updateUserModel(UserModel userModel);
}
