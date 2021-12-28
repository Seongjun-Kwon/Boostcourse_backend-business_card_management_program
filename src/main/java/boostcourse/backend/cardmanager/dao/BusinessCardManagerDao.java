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

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "SELECT name, phone, companyName, createDate FROM card where name LIKE ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            rs = ps.executeQuery();

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
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception exception) {
                }
            }

            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
        }

        return list;
    }

    public int addBusinessCard(BusinessCard businessCard) {
        int insertCount = 0;

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "INSERT INTO card (name, phone, companyName, createDate) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, businessCard.getName());
            ps.setString(2, businessCard.getPhone());
            ps.setString(3, businessCard.getCompanyName());
            ps.setString(4, businessCard.getCreateDate().toString());

            insertCount = ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception exception) {
                }
            }
        }


        return insertCount;
    }
}
