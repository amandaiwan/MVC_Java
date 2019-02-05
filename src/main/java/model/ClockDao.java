package model;

import model.dao.Clock;

import java.util.List;

public interface ClockDao {
    void createClock(Clock clock);
    void updateClock(Clock clock);
    List<Clock> getAllClocks();
}
