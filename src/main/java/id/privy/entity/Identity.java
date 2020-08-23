package id.privy.entity;

import id.privy.constant.Field;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class Identity {

    @Id
    @Column(name = Field.ID)
    protected Integer id;
}
