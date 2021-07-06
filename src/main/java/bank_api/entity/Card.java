package bank_api.entity;

import java.util.Objects;

public class Card {
    private Long id;
    private Long billId;
    private Long cardNumber;

    public Card() {
    }

    public Card(Long id, Long billId, Long cardNumber) {
        this.id = id;
        this.billId = billId;
        this.cardNumber = cardNumber;
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

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(billId, card.billId) && Objects.equals(cardNumber, card.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, billId, cardNumber);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", billId=" + billId +
                ", cardNumber=" + cardNumber +
                '}';
    }
}
