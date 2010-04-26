/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.cosmocode.palava.core.lifecycle.Initializable;
import de.cosmocode.palava.core.lifecycle.LifecycleException;

/**
 * A configurable implementation of the {@link JdbcService} interface
 * which relies on jdbc url configuration.
 *
 * @author Willi Schoenborn
 */
final class DefaultJdbcService implements JdbcService, Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultJdbcService.class);

    private final String url;
    
    private String testStatement = "SELECT 1";
    
    private Properties properties = new Properties();
    
    @Inject
    private DefaultJdbcService(@Named(JdbcConfig.URL) String url, @Named(JdbcConfig.DRIVER) Class<Driver> driver) {
        this.url = Preconditions.checkNotNull(url, "Url");
        Preconditions.checkNotNull(driver, "Driver");
        LOG.info("Configured {} with jdbc url {} and driver {}", new Object[] {
            this, url, driver
        });
    }
    
    @Inject(optional = true)
    void setTestStatement(@Named(JdbcConfig.TEST_STATEMENT) String testStatement) {
        this.testStatement = Preconditions.checkNotNull(testStatement, "TestStatement");
    }
    
    @Inject(optional = true)
    void setProperties(@Named(JdbcConfig.PROPERTIES) Properties properties) {
        this.properties = properties;
    }

    @Override
    public void initialize() throws LifecycleException {
        try {
            LOG.info("Testing connection");
            final Connection connection = connect();
            
            try {
                final Statement statement = connection.createStatement();
                LOG.debug("Testing connection with statement '{}'", testStatement);
                final ResultSet results = statement.executeQuery(testStatement);
                Preconditions.checkState(results.next());
                results.close();
                statement.close();
            } finally {
                connection.close();
            }
            
            LOG.info("Connection was valid");
        } catch (SQLException e) {
            throw new LifecycleException(e);
        }
    }

    @Override
    public Connection connect() throws SQLException {
        LOG.debug("Opening connection using {}", url);
        return DriverManager.getConnection(url, properties);
    }

}
