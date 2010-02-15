package de.cosmocode.palava.services.db;

import java.sql.Connection;

import com.google.inject.Provider;

import de.cosmocode.palava.core.Service;

/**
 * A {@link Service} interface providing {@link Connection}s.
 *
 * @author Willi Schoenborn
 */
public interface ConnectionProvider extends Service, Provider<Connection> {

}
