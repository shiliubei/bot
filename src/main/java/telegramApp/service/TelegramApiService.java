package telegramApp.service;

import telegramApp.dto.SongRequest;
import telegramApp.dto.SongResponce;
import telegramApp.model.TelegramMessage;

public interface TelegramApiService {

    public SongResponce sendAutorAndSongName(SongRequest telegramMessage);

    public SongRequest approveSong(SongRequest telegramMessage) ;
}
