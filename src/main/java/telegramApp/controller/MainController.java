package telegramApp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramApp.bot.Bot;
import telegramApp.model.TelegramUser;
import telegramApp.service.TelegramApiService;
import telegramApp.service.TelegramUserService;

@RestController
@RequestMapping(value = "/bot")
public class MainController {
    private final TelegramUserService telegramUserService;

    private final TelegramApiService telegramApiService;

    private final Bot bot;

    public MainController(TelegramUserService telegramUserService, TelegramApiService telegramApiService, Bot bot) {
        this.telegramUserService = telegramUserService;
        this.telegramApiService = telegramApiService;
        this.bot = bot;
    }

    @PostMapping(value = "/song")
    public void requestSong(@RequestBody TelegramUser tlgUser) {

        SendAudio sendAudio = new SendAudio();
        SendMessage response = new SendMessage();
        response.setChatId(tlgUser.getChatId());
        if (tlgUser.getTrack() != null) {

            sendAudio.setAudio(tlgUser.getTrack());
            sendAudio.setChatId(tlgUser.getChatId());

//            response.setText("Это нужная песня? (Введите \"да\" если это та песня)");

            telegramUserService.addTelegramUser(tlgUser);

            try {
                bot.execute(sendAudio);
//                bot.execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else {
            String text = " К сожалению такая песня не найдена. Введите другую.";
            response.setText(text);
            try {
                bot.execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping(value = "/approve")
    public void approved(@RequestBody TelegramUser tlgUser) {

        Long chatId = tlgUser.getChatId();
        SendMessage response = new SendMessage();
        response.setChatId(tlgUser.getChatId());
        response.setText("Всё ок");

        telegramUserService.deleteByChatId(chatId);
        try {
            bot.execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
