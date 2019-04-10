package com.anabol.userstore.dao.jdbc;

import com.anabol.userstore.dao.UserDao;
import com.anabol.userstore.dao.jdbc.mapper.UserRowMapper;
import com.anabol.userstore.entity.User;
import com.anabol.userstore.dao.jdbc.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private static final String FIND_ALL_QUERY = "SELECT id, firstName, lastName, salary, dateOfBirth FROM user";
    private static final String FIND_BY_ID_QUERY = "SELECT id, firstName, lastName, salary, dateOfBirth FROM user WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO user " +
            "(firstName, lastName, salary, dateOfBirth) " +
            "VALUES (:firstName, :lastName, :salary, :dateOfBirth)";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM user WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user SET firstName = ?, lastName = ?, salary = ?, dateOfBirth = ? WHERE id = ?";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, new UserRowMapper());
    }

//    @Override
//    public User findById(int id) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            return Mapper.parse(resultSet);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("We got SQLException", e);
//        }
//    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, new UserRowMapper(), id);
    }

    @Override
    public void insert(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", user.getFirstName());
        params.addValue("dateOfBirth", user.getDateOfBirth());
        namedParameterJdbcTemplate.update(INSERT_QUERY, params);
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            setStatementAttributes(preparedStatement, user);
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("We got SQLException", e);
        }
    }


    private void setStatementAttributes(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setDouble(3, user.getSalary());
        preparedStatement.setString(4, user.getDateOfBirth().toString());
    }
}
