package ru.trick.springmangabot.model;

//import jakarta.persistence.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private int balance;
    @Column(name = "Subscription")
    private boolean subscription;
//    @Column(name = "time_sub_before")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date time_sub_before;




    public User(long id, String name, int balance, boolean subscription) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.subscription = subscription;
    }

    public User () {}


//    public Date getTime_sub_before() {return time_sub_before;}
//
//    public void setTime_sub_before(Date time_sub_before) {this.time_sub_before = time_sub_before;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;

    }


}
