package id.privy.model;

import lombok.Data;

@Data
public class BalanceInput {

    private String username;
    private String code;
    private Double total;
    private String activity;
    private String type;
    private String location;
    private String agent;
}
