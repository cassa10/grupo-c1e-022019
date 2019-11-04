package com.desapp.grupoc1e022019.services.dtos;

public class WithdrawCreditDTO {

    private long idProvider;
    private Double amountToWithdraw;
    private String paymentMethod;
    private String destinationAddress;

    public WithdrawCreditDTO(){

    }

    public long getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(long idProvider) {
        this.idProvider = idProvider;
    }

    public Double getAmountToWithdraw() {
        return amountToWithdraw;
    }

    public void setAmountToWithdraw(Double amountToWithdraw) {
        this.amountToWithdraw = amountToWithdraw;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }
}
