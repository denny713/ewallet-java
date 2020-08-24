package id.privy.entity;

import id.privy.constant.Field;
import id.privy.constant.Tabel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = Tabel.USER)
public class User {

    @Id
    @Column(name = Field.ID)
    private Integer id;

    @Column(name = Field.USER)
    private String username;

    @Column(name = Field.EMAIL)
    private String email;

    @Column(name = Field.PASS)
    private String password;

    @Column(name = Field.STATUS)
    private String status;
}
