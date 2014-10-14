package com.ea.controller;

/**
 * Author Parham
 Since 8/9/14.
 */
import com.ea.model.*;
import com.ea.service.LiftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class LiftController {

    LiftService liftService;

    public LiftService getLiftService() {
        return liftService;
    }

    public void setLiftService(LiftService liftService) {
        this.liftService = liftService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostConstruct
    public void init(){
        liftService.initData();
    }

    @RequestMapping("/lift/{id}")
    public
    @ResponseBody
    Lift lift(@PathVariable("id") String id) {
        return liftService.getLift(id);
    }

    @RequestMapping("/floor/{id}")
    public
    @ResponseBody
    Floor floor(@PathVariable("id") Integer id) {
        return liftService.getFloor(id);
    }

    @RequestMapping("/lift")
    public
    @ResponseBody
    List<Lift> lift() {
        return liftService.getLifts();
    }

    @RequestMapping("/floor")
    public
    @ResponseBody
    List<Floor> floor() {
        return liftService.getFloors();
    }

    @RequestMapping(value = "/sendNearestLift/", method = {RequestMethod.POST})
    public
    @ResponseBody
    Lift send(@RequestBody MoveObject moveObject) {
        if(moveObject.getFrom() == moveObject.getTo())
            return null;
        Lift selectedLift = findNearestLift(moveObject.getFrom());
        if(selectedLift.getCurrentLevel() == moveObject.getFrom()){
            selectedLift.addDestination(new Destination(moveObject.getTo(), moveObject.getPeopleWaiting()));
            selectedLift.setCurrentCapacity(moveObject.getPeopleWaiting());
        }else{
            selectedLift.addDestination(new Destination(moveObject.getFrom(), 0));
            if(selectedLift.getStatus().equals(LiftStatus.STATIONARY)){
                if(selectedLift.getCurrentLevel() > moveObject.getFrom())
                    selectedLift.setStatus(LiftStatus.GOINGDOWN);
                else
                    selectedLift.setStatus(LiftStatus.GOINGUP);
            }
        }
        return liftService.updateLift(selectedLift);
    }

    //Find nearest elevator
    private Lift findNearestLift(Integer floorLevel) {
        Lift candidateLift = new Lift();
        Floor floor = liftService.getFloor(floorLevel);
        Integer distance = 10;
        for (Lift lift : liftService.getLifts()){
            if(lift.getCurrentCapacity() + floor.getPeople() < 20 && isFloorOnTheSamePath(lift, floor)){
                if(Math.abs(lift.getCurrentLevel() - floorLevel) < distance) {
                    distance = Math.abs(lift.getCurrentLevel() - floorLevel);
                    candidateLift = lift;
                }
            }
        }
        return candidateLift;
    }

    private boolean isFloorOnTheSamePath(Lift lift, Floor floor){
        return (lift.getCurrentLevel() - floor.getLevel() > 0 && lift.getStatus().equals(LiftStatus.GOINGDOWN)) ||
                (lift.getCurrentLevel() - floor.getLevel() < 0 && lift.getStatus().equals(LiftStatus.GOINGUP)) ||
                lift.getStatus().equals(LiftStatus.STATIONARY);
    }

    @RequestMapping(value = "/floor/", method = { RequestMethod.PUT })
    public
    @ResponseBody
    Floor updateFloor(@RequestBody Floor floor){
        return liftService.updateFloor(floor);
    }

}