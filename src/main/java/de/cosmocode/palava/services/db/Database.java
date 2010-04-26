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

package de.cosmocode.palava.services.db;

import javax.sql.DataSource;

import com.google.common.base.Supplier;

import de.cosmocode.palava.jdbc.JdbcService;

/**
 * A service used to provide access to a {@link DataSource}.
 *
 * @deprecated use {@link JdbcService} instead
 * @author Willi Schoenborn
 */
@Deprecated
public interface Database extends Supplier<DataSource> {

    /**
     * Provides a cached and configured {@link DataSource} instance.
     * {@inheritDoc}
     * @deprecated use {@link JdbcService} instead
     */
    @Deprecated
    @Override
    DataSource get();
    
}
