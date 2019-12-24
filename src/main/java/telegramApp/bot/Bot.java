package telegramApp.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramApp.dto.SongRequest;
import telegramApp.model.TelegramMessage;
import telegramApp.service.TelegramApiService;
import telegramApp.service.TelegramUserService;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;

        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        TelegramMessage telegramMessage = telegramUserService.findByChatId(chatId);

        BotContext context;
        BotState state;

        if (telegramMessage == null){
            state = BotState.geInitialState();

            telegramMessage = new TelegramMessage(chatId, state.ordinal());
            telegramUserService.addTelegramUser(telegramMessage);

            context = new BotContext(this, telegramMessage,text);
            state.enter(context);

        }else {
            context = new BotContext(this, telegramMessage,text);
            state = BotState.byId(telegramMessage.getStatetId());
        }

        state.handleInput(context);

       do {
            state = state.nextState();
            TelegramMessage tlgUser = context.getTelegramMessage();
//            if(context.getTelegramMessage().getStatetId()==2 && (tlgUser.getSongName()!= null || tlgUser.getPerformerName() !=null)){
//                SongRequest songRequestDto = new SongRequest(tlgUser);
//                telegramApiService.sendAutorAndSongName(tlgUser);
//            }
            state.enter(context);
        }
        while (!state.isInputNeeded());

        telegramMessage.setStatetId(state.ordinal());
        telegramUserService.updateTelegramUser(telegramMessage);
    }

    public void sendToServer (TelegramMessage telegramMessage){
        SongRequest songRequest = new SongRequest(telegramMessage);
        telegramApiService.sendAutorAndSongName(songRequest);
    }

    public void sendSongIdToServer (TelegramMessage telegramMessage){
        SongRequest songRequest = new SongRequest(telegramMessage);
        telegramApiService.approveSong(songRequest);
    }
    public TelegramMessage getTelegramMessageFromDB  (Long chatId){
        return telegramUserService.findByChatId(chatId);
    }


    //display keyboard buttons
    public ReplyKeyboardMarkup getCustomReplyKeyboardMarkup1() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton("Да"));
        keyboardRow1.add(new KeyboardButton("Нет"));
        keyboard.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
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
