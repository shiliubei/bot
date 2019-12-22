package telegramApp.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.File;

@Entity
public class TelegramUser {

    @Id
    private Long chatId;
    private int statetId;
    private String songName;
    private Long songId;
    @Transient
    private File track;

    public TelegramUser() {
    }

    public TelegramUser(Long chatId, int statetId) {
        this.chatId = chatId;
        this.statetId = statetId;
    }

    public int getStatetId() {
        return statetId;
    }

    public void setStatetId(int statetId) {
        this.statetId = statetId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
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
