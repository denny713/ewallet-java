package id.privy.entity;

import id.privy.constant.Field;
import id.privy.constant.Tabel;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = Tabel.BANK_BALANCE_HISTORY)
public class BankBalanceHistory {

    @Column(name = Field.ID)
    private Integer id;

    @Column(name = Field.BANK_BALANCE)
    private Integer bankBalanceId;

    @Column(name = Field.BEFORE)
    private Double balanceBefore;

    @Column(name = Field.AFTER)
    private Double balanceAfter;

    @Column(name = Field.ACTIVITY)
    private String activity;

    @Column(name = Field.TYPE)
    private String type;

    @Column(name = Field.IP)
    private String ip;

    @Column(name = Field.LOCATION)
    private String location;

    @Column(name = Field.AGENT)
    private String userAgent;

    @Column(name = Field.AUTHOR)
    private String author;

    @Id
    @Column(name = Field.ID_HISTORY)
    private String idHistory;
}
