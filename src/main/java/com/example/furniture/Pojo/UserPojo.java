package com.example.furniture.Pojo;
import com.example.furniture.Entity.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {
    private Integer id;

    private String email;
    private  String fullname;
    private  String password;



    public UserPojo(User user) {
        this.id=user.getId();
        this.email=user.getEmail();
        this.fullname=user.getFullname();
        this.password=user.getPassword();
    }
}
