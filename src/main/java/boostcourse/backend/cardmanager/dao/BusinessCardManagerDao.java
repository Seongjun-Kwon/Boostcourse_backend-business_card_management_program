package boostcourse.backend.cardmanager.dao;

import boostcourse.backend.cardmanager.dto.BusinessCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BusinessCardManagerDao {
    private static String dbUrl = "jdbc:mysql://localhost:3306/connectdb";
    private static String dbUser = "connectuser";
    private static String dbPassword = "connect123!@#";

    public List<BusinessCard> searchBusinessCard(String keyword) {
        List<BusinessCard> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "SELECT name, phone, companyName, createDate FROM card where name LIKE ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", new Locale("us"));

                while (rs.next()) {
                    String name = rs.getString(1);
                    String phone = rs.getString(2);
                    String companyName = rs.getString(3);
                    String date = rs.getString(4);
                    Date createDate = dateFormat.parse(date);

                    BusinessCard businessCard = new BusinessCard(name, phone, companyName, createDate);
                    list.add(businessCard);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int addBusinessCard(BusinessCard businessCard) {
        int insertCount = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO card (name, phone, companyName, createDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, businessCard.getName());
            ps.setString(2, businessCard.getPhone());
            ps.setString(3, businessCard.getCompanyName());
            ps.setString(4, businessCard.getCreateDate().toString());

            insertCount = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return insertCount;
    }
}
