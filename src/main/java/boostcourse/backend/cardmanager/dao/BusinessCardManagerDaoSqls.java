package boostcourse.backend.cardmanager.dao;

public class BusinessCardManagerDaoSqls {
    public static final String SELECT_ALL = "SELECT name, phone, companyName, createDate FROM card where name LIKE :keyword";
    public static final String INSERT_ONE = "INSERT INTO card (name, phone, companyName, createDate) VALUES (:name, :phone , :companyName , :createDate)";
}
