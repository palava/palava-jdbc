package de.cosmocode.palava.services.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Default connection provider using jdbc style urls.
 *
 * @author Willi Schoenborn
 */
final class DefaultConnectionProvider implements ConnectionProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultConnectionProvider.class);

    private final String jdbcUrl;
    
    @Inject
    public DefaultConnectionProvider(
        @Named("jdbc.drviver") String driver, @Named("jdbc.url") String jdbcUrl) throws SQLException {
        
        Preconditions.checkNotNull(driver, "Driver");
        this.jdbcUrl = Preconditions.checkNotNull(jdbcUrl, "JdbcUrl");
        
        try {
            LOG.info("Loading driver {}", driver);
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        
        testConnection();
    }
    
    private void testConnection() throws SQLException {
        LOG.info("Testing connection");
        final Connection connection = get();
        connection.createStatement().execute("SELECT 1");
        connection.close();
        LOG.info("Connection was valid");
    }
    
    @Override
    public Connection get() {
        try {
            LOG.debug("Opening connection using {}", jdbcUrl);
            return DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    
}
