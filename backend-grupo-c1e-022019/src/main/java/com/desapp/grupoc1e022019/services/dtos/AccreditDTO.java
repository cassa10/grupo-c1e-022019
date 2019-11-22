package com.desapp.grupoc1e022019.services.dtos;

public class AccreditDTO {

    private long id; //Id client
    private String googleId;
    private String tokenAccess;
    private Double amount;
    private String paymentMethod;
    private String originAddress;

    public AccreditDTO(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    @Override
    public String toString(){

        return "{ "+ "idClient: "+ id + " ,googleId: " + googleId +
                " ,tokenAccess: " + tokenAccess + " ,amount: "+ amount +
                " ,paymentMethod: " +paymentMethod  + " ,originAddress: "+ originAddress +" }";
    }
}
