package telegramApp.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import telegramApp.model.TelegramMessage;
import telegramApp.service.TelegramApiService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public enum BotState {


    Start (false){

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
            context.getTelegramMessage().setPerformerName(performerName);

        }

        @Override
        public BotState nextState() {
            return EnterSongName;
        }
    },

    EnterSongName {

        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Введите название песни:");
        }

        @Override
        public void handleInput(BotContext context) {

            context.getTelegramMessage().setSongName(context.getInput());
            context.getBot().sendToServer(context.getTelegramMessage());

        }


        @Override
        public BotState nextState() {
            return Approve;
        }
    },

    //Юзер получил звуковой файл для того чтобы уточнить нужная ли это песня.
    //Юзер должен нажать "да" если это та песня.
    Approve() {
        private BotState next;
        @Override
        public void enter(BotContext context) {
//            if(context.getTelegramMessage().getSongId()!=null){
//
//            }
            ReplyKeyboardMarkup customReplyKeyboardMarkup1 = context.getBot().getCustomReplyKeyboardMarkup1();
            SendMessage message = new SendMessage()
                    .setChatId(context.getTelegramMessage().getChatId())
                    .enableMarkdown(true)
                    .setReplyMarkup(customReplyKeyboardMarkup1)
                    .setText("Это нужная песня?");
            try {
                context.getBot().execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
        public void handleInput(BotContext context) {
            String text = context.getInput();

            if (text.equals("Да")) {
                TelegramMessage telegramMessage = context.getBot().getTelegramMessageFromDB(context.getTelegramMessage().getChatId());
                context.getBot().sendSongIdToServer(telegramMessage);
                next = Approved;
            } else {
                next = EnterPerformerName;
            }
        }

        @Override
        public BotState nextState() {
            return next;
        }

    },

    Approved(false) {

        @Override
        public void enter(BotContext context) {

            sendMessage(context, "Спасибо, всё ок. Вы можете заказаеть ещё одну.");
        }

        @Override
        public BotState nextState() {
            return EnterPerformerName;
        }

    };

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
                .setChatId(context.getTelegramMessage().getChatId())
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
    }

    public abstract void enter(BotContext context);

    public abstract BotState nextState();


}
