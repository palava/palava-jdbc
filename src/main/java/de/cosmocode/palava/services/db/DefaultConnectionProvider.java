/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
