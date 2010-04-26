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


/**
 * Static constant holder class for jdbc config key names.
 *
 * @author Willi Schoenborn
 */
public final class JdbcConfig {

    public static final String PREFIX = "jdbc.";
    
    public static final String URL = PREFIX + "url";
    
    public static final String DRIVER = PREFIX + "driver";
    
    public static final String TEST_STATEMENT = PREFIX + "testStatement";
    
    public static final String PROPERTIES = PREFIX + "properties";
    
    private JdbcConfig() {
        
    }
    
}
