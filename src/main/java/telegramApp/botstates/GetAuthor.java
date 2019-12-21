package telegramApp.botstates;

import telegramApp.bot.BotContext;
import telegramApp.bot.BotState;

public class GetAuthor implements BotState {
    @Override
    public void sendMessage(BotContext botContext, String text) {

    }

    @Override
    public void handleInput(BotContext botContext) {

    }

    @Override
    public void enter(BotContext botContext) {

    }

    @Override
    public BotState nextState() {
        return null;
    }

    @Override
    public boolean isInputNeeded() {
        return false;
    }
}
