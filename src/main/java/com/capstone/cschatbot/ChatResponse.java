package com.capstone.cschatbot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ChatResponse {
    private List<Choice> choices;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Choice {

        private int index;
        private Message message;
    }
}
