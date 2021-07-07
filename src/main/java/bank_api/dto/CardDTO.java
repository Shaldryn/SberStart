package bank_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardDTO {

    @JsonProperty("billId")
    private Long id;

    public CardDTO() {
    }

    public CardDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
