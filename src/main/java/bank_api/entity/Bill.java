package bank_api.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Bill {
    private Long id;
    private Long customerId;
    private BigDecimal billNumber;
    private BigDecimal balance;

    public Bill() {
    }

    public Bill(Long id, Long customerId, BigDecimal billNumber, BigDecimal balance) {
        this.id = id;
        this.customerId = customerId;
        this.billNumber = billNumber;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(BigDecimal billNumber) {
        this.billNumber = billNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(id, bill.id) && Objects.equals(customerId, bill.customerId) && Objects.equals(billNumber, bill.billNumber) && Objects.equals(balance, bill.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, billNumber, balance);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", billNumber=" + billNumber +
                ", balance=" + balance +
                '}';
    }
}
