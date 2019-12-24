package telegramApp.service;

import telegramApp.dto.SongRequest;
import telegramApp.model.TelegramMessage;

public interface TelegramApiService {

    public SongRequest sendAutorAndSongName(SongRequest telegramMessage);

    public SongRequest approveSong(SongRequest telegramMessage) ;
}
