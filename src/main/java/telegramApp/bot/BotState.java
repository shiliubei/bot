package telegramApp.bot;

public interface BotState {
    void sendMessage(BotContext botContext, String text);

    //    void nextState(BotContext botContext);
    void handleInput(BotContext botContext);

    void enter(BotContext botContext);

    BotState nextState();

    boolean isInputNeeded();

    public static void byStateName(String stateName){

    }
}
