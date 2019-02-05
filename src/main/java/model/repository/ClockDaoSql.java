package model.repository;

import connection.ConnectToDatabase;
import model.ClockDao;
import model.dao.Clock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClockDaoSql implements ClockDao {

    // `h15amaiw_klockor`
    private final Connection conn = ConnectToDatabase.createConnection();
    private final String SQL_CREATE_PRODUCT = "INSERT INTO h15amaiw_klockor (id, picText, picUrl, likes, dislikes) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_GET_ALL_PRODUCTS = "SELECT * FROM h15amaiw_klockor";
    private final String SQL_UPDATE_PRODUCT = "UPDATE h15amaiw_klockor SET picText=?, picUrl=?, likes=?, dislikes=? WHERE id=?";
    private final String SQL_DELETE_PRODUCT = "DELETE FROM products WHERE id=?";

    @Override
    public void createClock(Clock clock) {
    }

    @Override
    public void updateClock(Clock clock) {

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_PRODUCT)) {
            pstmt.setString(1, clock.getPicText());
            pstmt.setString(2, clock.getPicUrl());
            pstmt.setInt(3, clock.getLikes());
            pstmt.setInt(4, clock.getDislikes());
            pstmt.setInt(5, clock.getId());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ClockDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public List<Clock> getAllClocks() {
        List<Clock> allProducts= new ArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(SQL_GET_ALL_PRODUCTS);
             ResultSet rs = pstmt.executeQuery()) {
             while (rs.next()) {
                Clock product = new Clock();
                product.setId(rs.getInt(1));
                product.setPicText(rs.getString(2));
                product.setPicUrl(rs.getString(3));
                product.setLikes(rs.getInt(4));
                product.setDislikes(rs.getInt(5));
                allProducts.add(product);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClockDaoSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allProducts;
    }
}
