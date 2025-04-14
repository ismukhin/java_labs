package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="leaders_table")
public class UserInDB {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;
    int wins;


    public UserInDB() {
        this.id = -1;
        this.name = "default";
        wins = 0;
    }
    
    public UserInDB(String name, int wins) {
        this.id = -1;
        this.name = name;
        this.wins = wins;
    }

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }
    
    public int getWins() {
        return this.wins;
    }
    
    @Override
    public String toString() {
        return "User{" + "id =" + this.id + ", name = " + this.name + ", wins = " + this.wins + '}';
    }    
}
