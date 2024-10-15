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

public class ApplicationIDGenerator implements BeforeExecutionGenerator {

    private final Logger logger = LoggerFactory.getLogger(ApplicationIDGenerator.class);
    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
            EventType eventType) {

        String prefix = "SGDCAP";
        final String query = "SELECT MAX(application_id) FROM applications";
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
                            newId[0] = "SGDCAP0000";
                        }
                    } else {
                        // For the first student if no records are found
                        newId[0] = "SGDCAP0000";
                    }

                } catch (SQLException e) {
                    logger.error("SQL error while generating Student ID", e);
                    throw new RuntimeException("Error generating Application ID", e);
                }
            });
        } catch (Exception e) {
            logger.error("Failed to generate Application ID", e);
            throw new RuntimeException("Unable to generate Application ID", e);
        }

        return newId[0];
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(EventType.INSERT);
    }
}
