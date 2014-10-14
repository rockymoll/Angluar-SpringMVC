package com.ea.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Author Parham
 * Since 8/10/14.
 */
@Entity
public class Floor {
    @Id
    private Integer level;
    private Integer people;
    private Integer levelWishToGo;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer number) {
        this.level = number;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Integer getLevelWishToGo() {
        return levelWishToGo;
    }

    public void setLevelWishToGo(Integer levelWishToGo) {
        this.levelWishToGo = levelWishToGo;
    }
}
