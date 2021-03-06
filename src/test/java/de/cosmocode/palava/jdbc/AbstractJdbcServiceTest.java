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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cosmocode.junit.UnitProvider;
import de.cosmocode.palava.core.Framework;
import de.cosmocode.palava.core.Palava;

/**
 * Abstract test class for {@link JdbcService}s.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractJdbcServiceTest implements UnitProvider<JdbcService> {

    private final Framework framework = Palava.newFramework();
    
    public Framework getFramework() {
        return framework;
    }
    
    /**
     * Runs before each test.
     */
    @Before
    public void before() {
        framework.start();
    }
    
    /**
     * Tests creation of a new instance.
     */
    @Test
    public void create() {
        unit();
    }
    
    /**
     * Runs after each test.
     */
    @After
    public void after() {
        framework.stop();
    }

}
