package com.ea.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author Parham
   Since 8/9/14.
 */
@Entity
public class Lift {
    private LiftStatus status;
    private Integer currentCapacity;
    @Id
    private String name;
    private Integer currentLevel;

    public List<Destination> getDestinationList() {
        return new ArrayList<Destination>(destinationList);
    }

    public void setDestinationList(List<Destination> destinationList) {
        this.destinationList = destinationList;
    }

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Destination> destinationList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentFloor) {
        this.currentLevel = currentFloor;
    }

    public LiftStatus getStatus() {
        return status;
    }

    public void setStatus(LiftStatus status) {
        this.status = status;
    }

    public Integer getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(Integer currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public void addDestination(Destination d){
        destinationList.add(d);
        if(destinationList.size() == 1) {
            if(this.getCurrentLevel() > d.getLevel())
                this.setStatus(LiftStatus.GOINGDOWN);
            else
                this.setStatus(LiftStatus.GOINGUP);
        }
    }

    public void removeDestination(Destination d){
        destinationList.remove(d);
        if(destinationList.size() == 1) {
            if(this.getCurrentLevel() > d.getLevel())
                this.setStatus(LiftStatus.GOINGDOWN);
            else
                this.setStatus(LiftStatus.GOINGUP);
        }
    }

    public void clearDestinationList() {
        destinationList.clear();
    }
}
