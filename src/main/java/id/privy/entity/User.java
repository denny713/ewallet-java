package id.privy.entity;

import id.privy.constant.Field;
import id.privy.constant.Tabel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tabel.USER)
public class User extends Identity {

    @Column(name = Field.USER)
    private String username;

    @Column(name = Field.EMAIL)
    private String email;

    @Column(name = Field.PASS)
    private String password;

    @Column(name = Field.STATUS)
    private String status;
}
