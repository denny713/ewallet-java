package id.privy.entity;

import id.privy.constant.Field;
import id.privy.constant.Tabel;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = Tabel.BANK_BALANCE)
public class BankBalance {

    @Id
    @Column(name = Field.ID)
    private Integer id;

    @OneToMany(mappedBy = Field.ID, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @OrderBy("id ASC")
    private List<BankBalanceHistory> bankBalanceHistories;

    @Column(name = Field.BALANCE)
    private Double balance;

    @Column(name = Field.ACHIEVE)
    private Double achieve;

    @Column(name = Field.CODE)
    private String code;

    @Column(name = Field.ENABLE)
    private Boolean enable;

    @Column(name = Field.USER_ID)
    private Integer userId;

    @OneToOne(cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = Field.USER_ID, insertable = false, updatable = false, referencedColumnName = Field.ID)
    private User user;
}
