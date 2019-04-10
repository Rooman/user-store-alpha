package com.anabol.userstore.dao.jdbc.mapper;

import com.anabol.userstore.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setSalary(resultSet.getDouble("salary"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate dateOfBirth = LocalDate.parse(resultSet.getString("dateOfBirth"), dateTimeFormatter);
        user.setDateOfBirth(dateOfBirth);
        return user;
    }
}
