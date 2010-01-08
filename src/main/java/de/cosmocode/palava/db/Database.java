package de.cosmocode.palava.db;

import javax.sql.DataSource;

import com.google.common.base.Supplier;

import de.cosmocode.palava.Service;

/**
 * A {@link Service} used to provide access to a {@link DataSource}.
 *
 * @author Willi Schoenborn
 */
public interface Database extends Service, Supplier<DataSource> {

    /**
     * Provides a cached and configured {@link DataSource} instance.
     * {@inheritDoc}
     */
    @Override
    DataSource get();
    
}
