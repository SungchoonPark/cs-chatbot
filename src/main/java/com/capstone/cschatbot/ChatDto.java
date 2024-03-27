package com.capstone.cschatbot;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    static class ChatResp {
        private String question;
    }
}
