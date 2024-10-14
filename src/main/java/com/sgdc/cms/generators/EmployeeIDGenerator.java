package com.sgdc.cms.generators;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EmployeeIDGenerator
 */

public class EmployeeIDGenerator implements BeforeExecutionGenerator {

    private final Logger logger = LoggerFactory.getLogger(EmployeeIDGenerator.class);
    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
            EventType eventType) {

        String prefix = "EM";
        final String query = "SELECT MAX(employee_id) FROM EMPLOYEES";
        final String[] newId = new String[1]; // We need an array to capture the value from the lambda

        try {
            session.doWork(connection -> {
                try (PreparedStatement ps = connection.prepareStatement(query);
                        ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        String lastId = rs.getString(1);
                        if (lastId != null && lastId.startsWith(prefix)) {
                            // Extract numeric part and increment
                            String numericPart = lastId.substring(2); // remove "ST"
                            Long val = Long.parseLong(numericPart);
                            val = val + 1;
                            // Format the new ID
                            newId[0] = String.format("%s%d", prefix, val);
                        } else {
                            // If there are no IDs starting with ST
                            newId[0] = "EM60000001";
                        }
                    } else {
                        // For the first student if no records are found
                        newId[0] = "EM60000001";
                    }

                } catch (SQLException e) {
                    logger.error("SQL error while generating Employee ID", e);
                    throw new RuntimeException("Error generating Employee ID", e);
                }
            });
        } catch (Exception e) {
            logger.error("Failed to generate Employee ID", e);
            throw new RuntimeException("Unable to generate Employee ID", e);
        }

        return newId[0];
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }
}
