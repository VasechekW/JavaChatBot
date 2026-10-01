package main.java.chatbot.ui;

import main.java.chatbot.core.DataBaseBot;
import main.java.chatbot.io.UserInput;
import main.java.chatbot.io.UserOutput;

public class ChatBot {

    private final UserInput input;
    private final UserOutput output;
    private final DataBaseBot bot;
    private final MatrixDialogue matrixDialogue;


    public ChatBot(UserInput input,
                   UserOutput output,
                   DataBaseBot bot,
                   MatrixDialogue matrixDialogue){
        this.input = input;
        this.output = output;
        this.bot = bot;
        this.matrixDialogue = matrixDialogue;
    }

    public void run(){
        output.println("=== Чат-бот запущен ===");
        output.println("Напишите 'помощь' для списка команд или 'выход' для завершения.");

        boolean running = true;
        while (running){
            output.print("ВЫ: ");
            String question = input.readLine();

            if (question.isEmpty()){
                output.println("Бот: Вы ничего не ввели, попробуйте ещё раз.");
                continue;
            }

            if (question.equalsIgnoreCase("выход")){
                output.println("Бот: Пока! Хорошего дня.");
                running = false;
                continue;
            }


            switch (question.toLowerCase()) {
                case "операции с матрицами" -> matrixDialogue.run();

                // Переменная q здесь заменяет question внутри ветки
                case String q when bot.canAnswer(question) ->
                        output.println("Бот: " + bot.getAnswer(question));

                default ->
                        output.println("Бот: Извините, я не знаю ответа на вопрос \"" + question + "\".");
            }

        }
    }
}