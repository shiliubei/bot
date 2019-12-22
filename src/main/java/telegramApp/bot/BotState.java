package telegramApp.bot;

import telegramApp.model.TelegramUser;
import telegramApp.service.TelegramApiService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public enum BotState {


    EnterAuthorName {
        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return null;
        }

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Hello");
        }

        @Override
        public BotState nextState() {
            return EnterSongName;
        }
    },

    EnterSongName {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Введите название песни и исполнителя");
        }

        @Override
        public void handleInput(BotContext context) {
            //todo

//            context.getTelegramUser().setPhone(context.getInput());
            String songName = context.getInput();

            TelegramUser telegramUser = context.getTelegramUser();
            telegramUser.setSongName(songName);

            if (telegramApiService.sendSong(telegramUser) != null) {

            }
        }

        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return botContext.getTelegramUser();
        }

        @Override
        public BotState nextState() {
            return EnterMail;
        }
    },

    EnterMail {
        private BotState next;

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Enter your email address please:");
        }

        @Override
        public void handleInput(BotContext context) {
            String email = context.getInput();

            if (true) {
//                context.getTelegramUser().setEmail(context.getInput());
                next = Approved;
            } else {
                sendMessage(context, "Wrong e-mail address!");
                next = EnterMail;
            }
        }

        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return null;
        }

        @Override
        public BotState nextState() {
            return next;
        }
    },

    Approved(false) {
        @Override
        public TelegramUser sendToServer(BotContext botContext) {
            return null;
        }

        @Override
        public void enter(BotContext context) {

            sendMessage(context, "Thanks YOU! \n you can use command 'quote'!");
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
