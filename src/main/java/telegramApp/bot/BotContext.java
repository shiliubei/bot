package telegramApp.bot;

import telegramApp.model.TelegramUser;

public class BotContext {
//    private String botStateName;
//    private Bot bot;
//    private TelegramUser telegramUser;
//    private String input;
//
//
//    public BotContext(String botStateName, Bot bot, TelegramUser telegramUser, String input) {
//        this.botStateName = botStateName;
//        this.bot = bot;
//        this.telegramUser = telegramUser;
//        this.input = input;
//    }
//
//
//
//    public TelegramUser getTelegramUser() {
//        return telegramUser;
//    }
//
//    public String getInput() {
//        return input;
//    }

    private final Bot bot;
    private final TelegramUser telegramUser;
    private final String input;
    //?
    public static BotContext of(Bot bot, TelegramUser telegramUser, String text){
        return new BotContext(bot, telegramUser,text);
    }//?

    public BotContext(Bot bot, TelegramUser telegramUser, String input) {
        this.bot = bot;
        this.telegramUser = telegramUser;
        this.input = input;
    }

    public Bot getBot() {
        return bot;
    }

    public TelegramUser getTelegramUser() {
        return telegramUser;
    }

    public String getInput() {
        return input;
    }


}
