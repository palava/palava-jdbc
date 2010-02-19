package de.cosmocode.palava.services.db;

import java.sql.Connection;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * Binds the default provider for {@link Connection}s.
 * <p>
 *   Note: This mpodule does NOT bind to a provider. This should be done
 *   in application specific modules.
 * </p> 
 *
 * @author Willi Schoenborn
 */
final class ConnectionProviderModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(ConnectionProvider.class).to(DefaultConnectionProvider.class).in(Singleton.class);
    }

}
