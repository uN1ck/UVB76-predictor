package com.genseck.uvb76.predictor.wathcer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class UVB76LogsDto {

    private List<Message> messages;

    @Data
    public static class Message {
        private long date;
        @JsonProperty("edit_date")
        private long editDate;

        @JsonProperty("peerId")
        private long peerId;
        private long id;

        private String message;
    }

}
