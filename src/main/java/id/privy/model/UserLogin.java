package id.privy.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserLogin {

    private String username;
    private String email;
    private String status;
    private Date logedAt;
}
