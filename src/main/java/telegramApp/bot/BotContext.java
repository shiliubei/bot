package telegramApp.bot;

import telegramApp.model.TelegramUser;

public class BotContext {
    private String botStateName;
    private Bot bot;
    private TelegramUser telegramUser;
    private String input;
//    public BotContext (String botStateName){
//        this.botStateName = botStateName;
//    }

    public BotContext(String botStateName, Bot bot, TelegramUser telegramUser, String input) {
        this.botStateName = botStateName;
        this.bot = bot;
        this.telegramUser = telegramUser;
        this.input = input;
    }



    public TelegramUser getTelegramUser() {
        return telegramUser;
    }

    public String getInput() {
        return input;
    }




}
