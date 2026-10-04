package main.java.chatbot.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataBaseBot {

    private final Map<String, String> knowledge = new HashMap<>();

    public DataBaseBot(){
        knowledge.put("привет", "Привет! Чем могу помочь?");
        knowledge.put("как дела", "Отлично, спасибо! А у тебя?");
        knowledge.put("кто ты", "Я простой чат-бот на Java.");
        knowledge.put("пока", "До встречи!");
        knowledge.put("помощь", "Можешь спросить: привет, как дела, кто ты, пока, " +
                "а так же команда \"операции с матрицами\".");
    }

    public String getAnswer(String question){
        return knowledge.getOrDefault(question.toLowerCase(), "Я не знаю ответа на этот вопрос./n");
    }
}
