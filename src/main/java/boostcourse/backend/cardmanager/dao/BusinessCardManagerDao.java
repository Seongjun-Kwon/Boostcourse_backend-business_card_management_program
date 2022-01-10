package boostcourse.backend.cardmanager.dao;

import boostcourse.backend.cardmanager.dto.BusinessCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static boostcourse.backend.cardmanager.dao.BusinessCardManagerDaoSqls.INSERT_ONE;
import static boostcourse.backend.cardmanager.dao.BusinessCardManagerDaoSqls.SELECT_ALL;

@Repository
public class BusinessCardManagerDao {
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    public BusinessCardManagerDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<BusinessCard> searchBusinessCard(String keyword) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyword", "%" + keyword + "%");

        return jdbc.query(SELECT_ALL, params, new BusinessCardMapper());
    }

    public int addBusinessCard(BusinessCard businessCard) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", businessCard.getName());
        params.put("phone", businessCard.getPhone());
        params.put("companyName", businessCard.getCompanyName());
        params.put("createDate", businessCard.getCreateDate().toString());

        return jdbc.update(INSERT_ONE, params);
    }

    private static final class BusinessCardMapper implements RowMapper<BusinessCard> {
        public BusinessCard mapRow(ResultSet rs, int rowNum) throws SQLException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", new Locale("us"));

            String name = rs.getString(1);
            String phone = rs.getString(2);
            String companyName = rs.getString(3);
            String date = rs.getString(4);
            Date createDate = null;

            try {
                createDate = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return new BusinessCard(name, phone, companyName, createDate);
        }
    }
}
