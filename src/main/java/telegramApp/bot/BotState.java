package telegramApp.bot;

import telegramApp.model.TelegramUser;
import telegramApp.service.TelegramApiService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public enum BotState {


    Start (false){

        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return null;
        }

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Привет");
        }

        @Override
        public BotState nextState() {
            return EnterPerformerName;
        }
    },

    EnterPerformerName {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Введите исполнителя");
        }

        @Override
        public void handleInput(BotContext context) {
            String performerName = context.getInput();
            context.getTelegramUser().setPerformerName(performerName);
        }

        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return botContext.getTelegramUser();
        }

        @Override
        public BotState nextState() {
            return EnterSongName;
        }
    },

    EnterSongName {
        private BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Введите название песни:");
        }

        @Override
        public void handleInput(BotContext context) {

            context.getTelegramUser().setSongName(context.getInput());

        }

        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return null;
        }

        @Override
        public BotState nextState() {
            return Approve;
        }
    },

    //Юзер получил звуковой файл для того чтобы уточнить нужная ли это песня.
    //Юзер должен написать "да" если это та песня.
    Approve() {

        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return null;
        }

        @Override
        public void enter(BotContext context) {

            sendMessage(context, "Это нужная песня? (Введите \"да\" если это та песня)");
        }

        @Override
        public BotState nextState() {
            return null;
        }

    };

    private static TelegramApiService telegramApiService;

    private static BotState[] states;
    private final boolean inputNeeded;

    BotState() {
        this.inputNeeded = true;
    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }


    public static BotState geInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }

        return states[id];
    }

    protected void sendMessage(BotContext context, String text) {
        SendMessage message = new SendMessage()
                .setChatId(context.getTelegramUser().getChatId())
                .setText(text);
        try {
            context.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public void handleInput(BotContext context) {
        //default method
    }
    public abstract TelegramUser sendToServer(BotContext botContext);

    public abstract void enter(BotContext context);

    public abstract BotState nextState();


}
