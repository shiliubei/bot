package telegramApp.dto;

public abstract class TelegramBotMessage {
Long chatId;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
