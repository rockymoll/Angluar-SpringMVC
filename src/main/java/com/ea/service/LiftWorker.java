package com.ea.service;

import com.ea.model.Destination;
import com.ea.model.Floor;
import com.ea.model.Lift;
import com.ea.model.LiftStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author Parham
 * Since 8/10/14.
 */

@Component
public class LiftWorker{

    @Resource
    LiftService liftService;

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void run() {
        List<Lift> lifts = liftService.getLifts();
        for(Lift lift : lifts){
            moveLift(lift);
            lift = updatePassengers(lift);
            updateDirection(lift);
        }

    }

    private void updateDirection(Lift lift) {
       if(lift.getDestinationList().size() == 0){
           lift.setStatus(LiftStatus.STATIONARY);
       } else {
           if(lift.getCurrentLevel() == 0)
               lift.setStatus(LiftStatus.GOINGUP);
           if(lift.getCurrentLevel() == 10)
               lift.setStatus(LiftStatus.GOINGDOWN);
       }
       liftService.updateLift(lift);
    }

    private Lift updatePassengers(Lift lift) {
        Floor floor = liftService.getFloor(lift.getCurrentLevel());
        for(Destination destination : lift.getDestinationList()){
            if(destination.getLevel() != lift.getCurrentLevel())
                continue;
            lift.removeDestination(destination);
            if(destination.getOutPassengers() == 0){
                if(lift.getCurrentCapacity() + floor.getPeople() < 21) {
                    lift.setCurrentCapacity(lift.getCurrentCapacity() + floor.getPeople());
                    lift.addDestination(new Destination(floor.getLevelWishToGo(), floor.getPeople()));
                }
                else {
                    System.out.println("No Space in elevator " + lift.getName() + " at level " + floor.getLevel());
                }
            } else if(destination.getOutPassengers() > 0){
                lift.setCurrentCapacity(lift.getCurrentCapacity() - destination.getOutPassengers());
            }
        }
        if(lift.getCurrentCapacity() == 0 && lift.getDestinationList().size() == 0)  {
            lift.setStatus(LiftStatus.STATIONARY);
            lift.clearDestinationList();
        }

        return liftService.updateLift(lift);
    }

    private void moveLift(Lift lift) {
        switch (lift.getStatus()) {
            case GOINGDOWN:
                if(lift.getCurrentLevel()>1)
                    lift.setCurrentLevel(lift.getCurrentLevel() - 1);
                break;
            case GOINGUP:
                if(lift.getCurrentLevel()<10)
                    lift.setCurrentLevel(lift.getCurrentLevel() + 1);
                break;
            case STATIONARY:
                break;
            default:
                break;

        }
    }




}