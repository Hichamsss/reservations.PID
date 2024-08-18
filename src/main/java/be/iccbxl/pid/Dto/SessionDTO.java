package be.iccbxl.pid.Dto;

import java.util.Date;

public class SessionDTO {
    private String sessionId;
    private String productName;
    private int amount;
    private String currency;
    private Date createdDate;

    public SessionDTO() {
    }

    public SessionDTO(String sessionId, String productName, int amount, String currency, Date createdDate) {
        this.sessionId = sessionId;
        this.productName = productName;
        this.amount = amount;
        this.currency = currency;
        this.createdDate = createdDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
