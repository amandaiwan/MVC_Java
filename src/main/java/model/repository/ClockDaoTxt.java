package model.repository;

import model.ClockDao;
import model.dao.Clock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClockDaoTxt implements ClockDao {

    private List<Clock> allSavedFavorites = new ArrayList<>();

    @Override
    public void createClock(Clock clock) {

        BufferedWriter writer = null;
        boolean foundId = false;

        try {
                writer = Files.newBufferedWriter(Paths.get("favorites.txt"), StandardCharsets.ISO_8859_1, StandardOpenOption.APPEND);
                writer.write(clock.getId() + ";" + clock.getPicText() + ";" + clock.getPicUrl() + ";" + clock.getLikes() + ";" + clock.getDislikes());
                writer.newLine();
                writer.close();
                allSavedFavorites.add(clock);


            } catch(IOException ex){
                    Logger.getLogger(ClockDaoTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateClock(Clock clock) {

    }

    @Override
    public List<Clock> getAllClocks() {

        String textLine = null;
        Clock product = null;
        allSavedFavorites.clear();

        try {
            BufferedReader reader = null;
            reader = getReading();

            while ((textLine = reader.readLine()) != null) {
                String[] textRow = textLine.split(";");
                product = new Clock(Integer.parseInt(textRow[0]), textRow[1], textRow[2], Integer.parseInt(textRow[3]), Integer.parseInt(textRow[4]));
                allSavedFavorites.add(product);
            }
            reader.close();

        } catch (IOException e) {
            System.out.println(ClockDaoTxt.class.getName() + e.getMessage());
        }
        return allSavedFavorites;
    }

    private BufferedReader getReading() {

        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(Paths.get("favorites.txt"), StandardCharsets.ISO_8859_1);
        } catch (IOException ex) {
            Logger.getLogger(ClockDaoTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reader;
    }
}
