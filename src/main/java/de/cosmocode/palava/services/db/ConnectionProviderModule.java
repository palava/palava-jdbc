package de.cosmocode.palava.services.db;

import java.sql.Connection;

import de.cosmocode.palava.core.ServiceModule;

/**
 * Binds the default provider for {@link Connection}s.
 * <p>
 *   Note: This mpodule does NOT bind to a provider. This should be done
 *   in application specific modules.
 * </p> 
 *
 * @author Willi Schoenborn
 */
final class ConnectionProviderModule extends ServiceModule {

    @Override
    protected void configure() {
        serve(ConnectionProvider.class).with(DefaultConnectionProvider.class);
    }

}
