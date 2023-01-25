package ru.trick.springmangabot.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chapterManga")
public class ChapterManga {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "url")
    private String url;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;

    public ChapterManga(){}
    public ChapterManga(int number, String url, String name, int price) {
        this.number = number;
        this.url = url;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
