package ru.trick.springmangabot.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "alert")
public class Alert {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nameManga")
    private String nameManga;

    @Column(name = "alertStatus")
    private boolean alert;

    @Column(name = "chatId")
    private Long chatId;


    public Alert () {}
    public Alert(String nameManga, boolean alert) {
        this.nameManga = nameManga;
        this.alert = alert;
    }

    public Alert(String nameManga, boolean alert, Long chatId) {
        this.nameManga = nameManga;
        this.alert = alert;
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameManga() {
        return nameManga;
    }

    public void setNameManga(String nameManga) {
        this.nameManga = nameManga;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }
}
