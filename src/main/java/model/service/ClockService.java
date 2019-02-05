package model.service;

import model.dao.Clock;
import model.repository.ClockDaoSql;
import model.repository.ClockDaoTxt;

import java.util.List;

public class ClockService {

    ClockDaoSql clockSQL = new ClockDaoSql();
    ClockDaoTxt clockTxt = new ClockDaoTxt();

    public List<Clock> findAllProducts(){
        return clockSQL.getAllClocks();
    }

    public void updateProduct(Clock clock){
        clockSQL.updateClock(clock);
    }

    public void addToFavorites(Clock clock){
        clockTxt.createClock(clock);
    }

    public List<Clock> showAllFavoriteClocks(){
        return clockTxt.getAllClocks();
    }
}
