package app.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoardPost {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final int id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime updatedAt;

    public BoardPost(int id, String title, String author, String content, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFormattedDate() {
        return updatedAt.format(FORMATTER);
    }
}
