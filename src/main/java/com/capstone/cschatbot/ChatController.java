package com.capstone.cschatbot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/api/initial")
    public ChatDto.ChatResp initialChat() {
        String chat = chatService.initialChat();
        return ChatDto.ChatResp.builder()
                .question(chat)
                .build();
    }

    @GetMapping("/api/chat")
    public ChatDto.ChatResp chat(@RequestParam String prompt) {
        log.info("prompt : " + prompt);
        String chat = chatService.chat(prompt);
        return ChatDto.ChatResp.builder()
                .question(chat)
                .build();
    }
}
