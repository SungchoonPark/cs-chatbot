package com.capstone.cschatbot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    @Value("${openai.url}")
    private String apiUrl;
    @Value("${openai.model}")
    private String model;
    @Qualifier("chatRestTemplate")
    @Autowired
    private final RestTemplate restTemplate;

    public String initialChat( ) {
        ChatRequest chatRequest = new ChatRequest(model, createInitialQuestion());
        ChatResponse chatResponse = restTemplate.postForObject(apiUrl, chatRequest, ChatResponse.class);
        String content = chatResponse.getChoices().get(0).getMessage().getContent();
        return content;
    }

    public String chat(String prompt) {
        //
        String realPrompt = createPrompt(prompt);
        log.info("realPrompt : " + realPrompt);

        ChatRequest chatRequest = new ChatRequest(model, realPrompt);
        log.info("[BEFORE ENTER]");
        ChatResponse response = restTemplate.postForObject(apiUrl, chatRequest, ChatResponse.class);
        log.info("[RESPONSE] : " + response);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "[THIS IS NULL EXCEPTION]";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }

    /***
     * 프롬프트 계속 업그레이드 해야됨
     * 동시에 파인튜닝 데이터를 계속 업데이트 해줘야함 ver1. 주제당 20개
     *
     * 프롬프트 갯수는 주제 갯수마다 똑같아야함.
     *
     * ver1 -> 꼬리물기로만 일단 완성해보기 (운체, 컴네, 데베, 자구)
     * ver2 -> 꼬리물기가 아닌 단답형 와리가리 (운체, 컴네, 데베, 자구)
     * ver3 -> 주제를 추가하는 방안. (알고리즘)
     *      -> while(새로운 기능들 생성)
     *
     * 역할
     * 1. 프롬프팅만 주구장창 하는놈 + 파인튜닝 데이터셋 계속 추가
     * 2. 앱 (권예씨 + 성춘씨)
     * 3. 서버
     */
    public String createPrompt(String answer) {
        String prompt = "이제부터 너는 네이버 백엔드 개발자 채용을 담당하는 면접관이다.\n" +
                "너는 내가 운영체제 전반에 대한 지식을 잘 알고 있는지 확인을 하고 싶어한다.\n" +
                "나한테 운영체제 관련 질문을 해라\n" +
                "그리고 내가 질문에 답변을 하면 내가 답변을 한 내용을 바탕으로 또 다른 질문을 해라\n" +
                "그리고 대화를 실제 면접 상황이라고 가정하고 질문을 해라\n" +
                "그리고 질문을 할 때 '1. 운영체는 무엇인가요?' 이런 식으로 숫자를 쓰지 말고 실제 면접관 말투로 질문해라.\n" +
                "내가 모른다고 하면 설명해줄 필요는 없고 다음 질문으로 넘어가라\n" +
                "이 면접은 적어도 100개의 운영체제 관련 질문을 한 후에 종료한다.\n" +
                "그리고 내가 종료라고 하면 이 면접은 끝난다.";

        return prompt + answer;
    }

    public String createInitialQuestion() {
        String tmp = "너는 이제부터 컴퓨터 네트워크에 대한 면접을 진행할 면접관이야. 면접을 시작하기에 앞서 면접이 준비되었는지 물어봐줘." +
                "면접 준비가 되었다다는 답이 돌아오면 그 이후로 네트워크 관련 질문을 시작해줘.";
        return tmp;
    }

}
