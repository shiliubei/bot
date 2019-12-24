package telegramApp.service;

import org.springframework.stereotype.Service;
import telegramApp.model.TelegramMessage;
import telegramApp.repo.TelegramUserRepo;

import javax.transaction.Transactional;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {
    private TelegramUserRepo telegramUserRepo;

    public TelegramUserServiceImpl(TelegramUserRepo telegramUserRepo){
        this.telegramUserRepo = telegramUserRepo;
    }

    @Transactional
    public TelegramMessage findByChatId(long id){
        return telegramUserRepo.findByChatId(id);
    }

    public void deleteByChatId (Long id){
        telegramUserRepo.delete(telegramUserRepo.findByChatId(id));
    }
    @Transactional
    public void addTelegramUser (TelegramMessage telegramMessage){
        telegramUserRepo.save(telegramMessage);
    }

    @Override
    public void updateTelegramUser(TelegramMessage telegramMessage) {
        telegramUserRepo.save(telegramMessage);
    }
}
