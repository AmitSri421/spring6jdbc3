package com.pluralsight.conference.repository;

import com.pluralsight.conference.model.Speaker;
import com.pluralsight.conference.repository.util.SpeakerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("speakerRepository")
public class SpeakerRepositoryImpl implements SpeakerRepository {

    private JdbcTemplate jdbcTemplate;

    public SpeakerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Speaker> findAll() {
//        Speaker speaker = new Speaker();
//        speaker.setName("Bryan Hansen");
//        speaker.setSkill("Java");
//        List<Speaker> speakers = new ArrayList<>();
//        speakers.add(speaker);

        // RowMapper lambda
        /*
        RowMapper<Speaker> rowMapper = (rs, rowNum) -> {
            Speaker speaker = new Speaker();
            speaker.setId(rs.getInt("id"));
            speaker.setName(rs.getString("name"));
            return speaker;
        };
         */

        List<Speaker> speakers = jdbcTemplate.query("select * from speaker", new SpeakerRowMapper());
        return speakers;
    }

    @Override
    public Speaker create(Speaker speaker) {
        // jdbcTemplate.update("INSERT INTO SPEAKER (NAME) VALUES (?)", speaker.getName());

        // JDBC template to create and read single row
        /*
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("INSERT INTO SPEAKER (NAME) VALUES (?)", new String[] {"id"});
                ps.setString(1, speaker.getName());
                return ps;
            }
        }, keyHolder);
        Number id = keyHolder.getKey();
        */

        // Simple JDBC insert to create. Advantage is it returns the key.
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.setTableName("speaker");
        List<String> columns = new ArrayList<>();
        columns.add("name");
        insert.setColumnNames(columns);

        Map<String, Object> data = new HashMap<>();
        data.put("name", speaker.getName());

        insert.setGeneratedKeyName("id");
        Number id = insert.executeAndReturnKey(data);

        return getSpeaker(id.intValue());
    }

    @Override
    public Speaker getSpeaker(int id) {
        return jdbcTemplate.queryForObject("select * from speaker where id = ?", new SpeakerRowMapper(), id);
    }

    @Override
    public Speaker update(Speaker speaker) {
        jdbcTemplate.update("UPDATE SPEAKER SET NAME = ? WHERE ID = ?", speaker.getName(), speaker.getId());
        return speaker;
    }

    @Override
    public void update(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("UPDATE SPEAKER SET SKILL = ? WHERE ID = ?", pairs);
    }
}
