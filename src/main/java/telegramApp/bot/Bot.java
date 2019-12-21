package telegramApp.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramApp.botstates.BotStart;
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
//        if (update.hasMessage()) {
//
//            Message message = update.getMessage();
//            Long chatId = message.getChatId();
//            String text = message.getText();
//            SendMessage response = new SendMessage();
//            response.setChatId(chatId);
//            if (telegramUserService.findByChatId(chatId) == null) {
//                TelegramUser telegramUser = new TelegramUser();
//                telegramUser.setChatId(message.getChatId());
//                telegramUser.setSongName(message.getText());
//                telegramApiService.sendSong(telegramUser);
//            } else {
//                if (text.equals("да")) {
//                    TelegramUser user = telegramUserService.findByChatId(chatId);
//                    telegramApiService.approveSong(user);
//                } else {
//                    telegramUserService.deleteByChatId(chatId);
//                    response.setText(" Введите название песни");
//                    try {
//                        execute(response);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void onUpdateReceived(Update update) {
       if(update.hasMessage()){
           Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text = message.getText();
//            BotContext context;
//            context = new BotContext(this, )
//            BotState state = new BotStart(context);
//
//           state.handleInput(context);
//
//           do {
//               state = state.nextState();
//               state.enter(context);
//           }while (!state.isInputNeeded());

       }
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
