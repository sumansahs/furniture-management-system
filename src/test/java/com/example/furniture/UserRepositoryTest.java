package com.example.furniture;



import com.example.furniture.Entity.User;
import com.example.furniture.Repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepo userRepo;


    @Test
    @Order(1)
    public void saveUserTest() {

//        User User = new User();
//        User.setFullname("rak");

        User user = User.builder()
                .fullname("rak")
                .email("a@g.com")
                .password("1234")
                .build();


        userRepo.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getUserTest() {

        User user = User.builder()
                .fullname("rak")
                .email("a@g.com")
                .password("1234")
                .build();


        userRepo.save(user);


        User userCreated = userRepo.findById(user.getId()).get();
        Assertions.assertThat(userCreated.getId()).isEqualTo(user.getId());

    }

    @Test
    @Order(3)
    public void getListOfUserTest(){
        User user = User.builder()
                .fullname("rak")
                .email("a@g.com")
                .password("1234")
                .build();


        userRepo.save(user);
        List<User> User = userRepo.findAll();
        Assertions.assertThat(User.size()).isGreaterThan(0);
    }


    @Test
    @Order(4)
    public void updateUserTest(){

        User user = User.builder()
                .fullname("rak")
                .email("a@g.com")
                .password("1234")
                .build();


        userRepo.save(user);

        User user1  = userRepo.findById(user.getId()).get();

        user1.setFullname("new name");

        User userUpdated  = userRepo.save(user);

        Assertions.assertThat(userUpdated.getFullname()).isEqualTo("suman");

    }

    @Test
    @Order(5)
    public void deleteUserTest(){

        User user = User.builder()
                .fullname("rak")
                .email("a@g.com")
                .password("1234")
                .build();


        userRepo.save(user);

//        @Query(value = "SELECT * from")

        User user1 = userRepo.findById(user.getId()).get();
//        User user1 = userRepo.findById(user.getId()).get();
        userRepo.delete(user1);

        User user2 = null;
        Optional<User> optionalUser = userRepo.findByEmail("a@g.com");
        if(optionalUser.isPresent()){
            user2 = optionalUser.get();
        }

        Assertions.assertThat(user2).isNull();
//        Assertions.assertThat(User1.getId()).isNull();
    }
}