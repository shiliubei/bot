package telegramApp.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramApp.model.TelegramUser;
import telegramApp.service.TelegramApiService;
import telegramApp.service.TelegramUserService;

@Component
public class Bot extends TelegramLongPollingBot {


    private final TelegramUserService telegramUserService;


    private final TelegramApiService telegramApiService;

    public void soundExecute(SendAudio sendAudio){
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Bot(TelegramUserService telegramUserService, TelegramApiService telegramApiService) {
        this.telegramUserService = telegramUserService;
        this.telegramApiService = telegramApiService;
    }



//    @Override
//    public void onUpdateReceived(Update update) {
//       if(update.hasMessage()){
//           Message message = update.getMessage();
//            Long chatId = message.getChatId();
//            String text = message.getText();
//
//       }
//    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;

        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        TelegramUser telegramUser = telegramUserService.findByChatId(chatId);



        BotContext context;
        BotState state;

        if (telegramUser == null){
            state = BotState.geInitialState();

            telegramUser = new TelegramUser(chatId, state.ordinal());
            telegramUserService.addTelegramUser(telegramUser);

            context = BotContext.of(this, telegramUser,text);
            state.enter(context);

//            LOGGER.info("New telegramUser registered: "+ chatId);
        }else {
            context = BotContext.of(this, telegramUser,text);
            state = BotState.byId(telegramUser.getStatetId());

//            LOGGER.info("Update received for telegramUser in state: "+ state);
        }

        state.handleInput(context);

        do {
            state = state.nextState();
            TelegramUser tlgUser = state.sendToServer(context);
            if(tlgUser != null){
                telegramApiService.sendSong(tlgUser);
            }
            state.enter(context);
        }while (!state.isInputNeeded());

        telegramUser.setStatetId(state.ordinal());
//        telegramUserService.updateUser(telegramUser);
    }


    @Override
    public String getBotToken() {
        return "984357723:AAFxB5Vx-l8sl675bG1zcqGwZ1YlznGUSpA";
    }

    @Override
    public String getBotUsername() {
        return "SongName_bot";
    }
}
