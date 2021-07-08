package bank_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CardDTO {

    @JsonProperty("cardId")
    private Long id;

    @JsonProperty("billId")
    private Long billId;

    @JsonProperty("sum")
    private BigDecimal sum;

    public CardDTO() {
    }

    public CardDTO(Long id, Long billId, BigDecimal sum) {
        this.id = id;
        this.billId = billId;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
