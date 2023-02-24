package com.example.furniture.Services;

import com.example.furniture.Entity.User;
import com.example.furniture.Pojo.UserPojo;

public interface UserServices {
    UserPojo save (UserPojo userPojo);
    User findByEmail(String email);

    User findBYId(Integer id);


}
