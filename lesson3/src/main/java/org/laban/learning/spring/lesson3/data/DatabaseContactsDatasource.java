package org.laban.learning.spring.lesson3.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.laban.learning.spring.lesson3.data.mapper.ContactMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@RequiredArgsConstructor
@Repository
public class DatabaseContactsDatasource implements ContactsDatasource {
    private final JdbcTemplate jdbcTemplate;
    private final ContactMapper contactMapper = new ContactMapper();

    @Override
    public List<Contact> contacts() {
        return jdbcTemplate.queryForStream("SELECT * FROM contacts", contactMapper).toList();
    }

    @Override
    public Contact findContactById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM contacts WHERE id = ?", contactMapper, id);
    }

    @Override
    public void addContact(Contact contact) {
        jdbcTemplate.update(
                "INSERT INTO contacts (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone()
        );
    }

    @Override
    public void updateContact(Contact contact) {
        jdbcTemplate.update(
                "UPDATE contacts " +
                        "SET first_name = ?, last_name = ?, email = ?, phone = ?" +
                        "WHERE id = ?",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getId()
        );
    }

    @Override
    public void deleteContactById(long id) {
        jdbcTemplate.update("DELETE FROM contacts WHERE id = ?", id);
    }
}