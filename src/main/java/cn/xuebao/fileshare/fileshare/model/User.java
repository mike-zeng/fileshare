package cn.xuebao.fileshare.fileshare.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 表示用户
 * 说明：role的取值有三个，分别为0,1,2
 * role的默认取值为0，表示一个普通用户，其他取值的意义如下
 * 1：管理员用户
 * 2：根用户
 */
@Validated
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Email
    private String username;

    @Length(min = 6,max = 16)
    private String password;

    private String repeatPassword;

    private String salt;

    private Integer id;

    public User(){

    }
}
