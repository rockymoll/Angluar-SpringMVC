package com.ea.service;

import com.ea.model.Floor;
import com.ea.model.Lift;
import com.ea.model.LiftStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Author Parham
 * Since 8/10/14.
 */
public class LiftServiceImpl implements LiftService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void initData(){
        Lift liftA = new Lift();
        liftA.setName("A");
        liftA.setCurrentCapacity(0);
        liftA.setCurrentLevel(1);
        liftA.setStatus(LiftStatus.STATIONARY);
        entityManager.persist(liftA);

        Lift liftB = new Lift();
        liftB.setName("B");
        liftB.setCurrentCapacity(0);
        liftB.setCurrentLevel(2);
        liftB.setStatus(LiftStatus.GOINGUP);
        entityManager.persist(liftB);

        Lift liftC = new Lift();
        liftC.setName("C");
        liftC.setCurrentCapacity(0);
        liftC.setCurrentLevel(7);
        liftC.setStatus(LiftStatus.GOINGDOWN);
        entityManager.persist(liftC);

        Lift liftD = new Lift();
        liftD.setName("D");
        liftD.setCurrentCapacity(0);
        liftD.setCurrentLevel(8);
        liftD.setStatus(LiftStatus.GOINGDOWN);
        entityManager.persist(liftD);

        Floor floor1 = new Floor();
        floor1.setLevel(1);
        floor1.setLevelWishToGo(0);
        floor1.setPeople(0);
        entityManager.persist(floor1);

        Floor floor2 = new Floor();
        floor2.setLevel(2);
        floor2.setLevelWishToGo(0);
        floor2.setPeople(0);
        entityManager.persist(floor2);

        Floor floor3 = new Floor();
        floor3.setLevel(3);
        floor3.setLevelWishToGo(2);
        floor3.setPeople(3);
        entityManager.persist(floor3);

        Floor floor4 = new Floor();
        floor4.setLevel(4);
        floor4.setLevelWishToGo(0);
        floor4.setPeople(0);
        entityManager.persist(floor4);

        Floor floor5 = new Floor();
        floor5.setLevel(5);
        floor5.setLevelWishToGo(10);
        floor5.setPeople(4);
        entityManager.persist(floor5);

        Floor floor6 = new Floor();
        floor6.setLevel(6);
        floor6.setLevelWishToGo(0);
        floor6.setPeople(0);
        entityManager.persist(floor6);

        Floor floor7 = new Floor();
        floor7.setLevel(7);
        floor7.setLevelWishToGo(0);
        floor7.setPeople(0);
        entityManager.persist(floor7);

        Floor floor8 = new Floor();
        floor8.setLevel(8);
        floor8.setLevelWishToGo(0);
        floor8.setPeople(0);
        entityManager.persist(floor8);

        Floor floor9 = new Floor();
        floor9.setLevel(9);
        floor9.setLevelWishToGo(3);
        floor9.setPeople(5);
        entityManager.persist(floor9);

        Floor floor10 = new Floor();
        floor10.setLevel(10);
        floor10.setLevelWishToGo(0);
        floor10.setPeople(0);
        entityManager.persist(floor10);
    }

    @Override
    public void updateLiftStatus(){
        entityManager.getReference(Lift.class, "A");
    }

    @Override
    public List<Lift> getLifts() {
        Query query = entityManager.createQuery("SELECT e FROM Lift e ORDER BY e.name");
        return (List<Lift>) query.getResultList();
    }

    @Override
    public List<Floor> getFloors() {
        Query query = entityManager.createQuery("SELECT e FROM Floor e");
        return (List<Floor>) query.getResultList();
    }

    @Override
    public Lift getLift(String q) {
        Query query = entityManager.createQuery("SELECT e FROM Lift e where e.name = ?1");
        query.setParameter(1,q);
        return (Lift) query.getSingleResult();
    }

    @Override
    public Floor getFloor(Integer q) {
        Query query = entityManager.createQuery("SELECT e FROM Floor e where e.level = ?1");
        query.setParameter(1,q);
        return (Floor) query.getSingleResult();
    }

    @Override
    @Transactional
    public Lift updateLift(Lift lift) {
        return entityManager.merge(lift);
    }

    @Override
    @Transactional
    public Floor updateFloor(Floor floor) {
        Floor managedFloor = getFloor(floor.getLevel());
        managedFloor.setPeople(floor.getPeople());
        managedFloor.setLevelWishToGo(floor.getLevelWishToGo());
        return entityManager.merge(floor);
    }
}
