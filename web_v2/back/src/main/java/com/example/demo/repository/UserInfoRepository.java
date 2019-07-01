package com.example.demo.repository;

import com.example.demo.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

//    @Query("")
//    void setAvatar(String username, Binary avatar);
//
//    byte[] getAvatar(String username);

    UserInfo findByUsername(String username);
}
