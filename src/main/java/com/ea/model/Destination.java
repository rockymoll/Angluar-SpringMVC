package com.ea.model;

import javax.persistence.*;

/**
 * Author Parham
 * Since 8/10/14.
 */
@Entity
public class Destination {

    private int level;
    private int outPassengers;
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private long id;

    public Destination() {
    }

    public Destination(int level, int outPassengers) {
        this.level = level;
        this.outPassengers = outPassengers;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOutPassengers() {
        return outPassengers;
    }

    public void setOutPassengers(int passengers) {
        this.outPassengers = passengers;
    }

}
