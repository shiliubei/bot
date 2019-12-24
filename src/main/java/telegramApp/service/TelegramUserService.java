package telegramApp.service;

import telegramApp.model.TelegramMessage;

public interface TelegramUserService {

    TelegramMessage findByChatId(long id);

    void deleteByChatId(Long id);

    void addTelegramUser(TelegramMessage telegramMessage);

    void updateTelegramUser(TelegramMessage telegramMessage);
}
