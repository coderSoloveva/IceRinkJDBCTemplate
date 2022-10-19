package ru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.dto.request.AreaDtoRequest;
import ru.dto.response.AreaDtoResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class AreaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AreaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AreaDtoResponse> getAreas() throws SQLException {
        String SQL = "SELECT * FROM area";
        return  jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(AreaDtoResponse.class));
    }

    public AreaDtoResponse getAreaById(Integer id) throws SQLException {
        String SQL = "SELECT * FROM area WHERE id = " + id;
        return  jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(AreaDtoResponse.class)).stream().findAny().orElse(null);
    }

    public AreaDtoResponse createArea(AreaDtoRequest areaDtoRequest) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        String SQL = "INSERT INTO area (name, city_id) VALUES (?, ?)";
                        PreparedStatement preparedStatement =
                                connection.prepareStatement(SQL,  new String[] {"id"});
                        preparedStatement.setString(1, areaDtoRequest.getName());
                        preparedStatement.setInt(2, areaDtoRequest.getCity_id());
                        return preparedStatement;
                    }
                },
                keyHolder);
        AreaDtoResponse areaDtoResponse =  areaDtoRequestToResponse(areaDtoRequest);
        Integer id = keyHolder.getKey().intValue();
        areaDtoResponse.setId(id);
        return areaDtoResponse;
    }

    public void deleteArea(Integer id) throws SQLException {
        String SQL = "DELETE FROM area WHERE id = " + id;
        jdbcTemplate.update(SQL);
    }

    private AreaDtoResponse areaDtoRequestToResponse(AreaDtoRequest areaDtoRequest){
        AreaDtoResponse areaDtoResponse = new AreaDtoResponse();
        areaDtoResponse.setCity_id(areaDtoRequest.getCity_id());
        areaDtoResponse.setName(areaDtoRequest.getName());
        return areaDtoResponse;
    }
}
