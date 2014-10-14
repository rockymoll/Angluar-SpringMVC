package com.ea.service;

import com.ea.model.Floor;
import com.ea.model.Lift;
import com.ea.model.LiftStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Author Parham
 * Since 8/10/14.
 */
public interface LiftService {
    @PostConstruct
    @Transactional
    void initData();

    void updateLiftStatus();

    List<Lift> getLifts();

    List<Floor> getFloors();

    Lift getLift(String q);

    Floor getFloor(Integer q);

    @Transactional
    Lift updateLift(Lift lift);

    @Transactional
    Floor updateFloor(Floor floor);
}
