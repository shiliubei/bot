package telegramApp.dto;

import java.io.File;

public class SongResponce {

    private Long chatId;
    private Long songId;
    private File track;

    public SongResponce() {
    }

    public SongResponce(Long chatId, Long songId, File track) {
        this.chatId = chatId;
        this.songId = songId;
        this.track = track;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public File getTrack() {
        return track;
    }

    public void setTrack(File track) {
        this.track = track;
    }
}
