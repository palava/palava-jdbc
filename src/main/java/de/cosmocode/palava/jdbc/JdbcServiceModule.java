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

import java.lang.annotation.Annotation;
import java.sql.Driver;
import java.util.Properties;

import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import de.cosmocode.palava.core.inject.AbstractRebindingModule;
import de.cosmocode.palava.core.inject.Config;
import de.cosmocode.palava.core.inject.RebindModule;

/**
 * A module for {@link JdbcService}s.
 *
 * @author Willi Schoenborn
 */
public final class JdbcServiceModule implements Module {

    private static final TypeLiteral<Class<Driver>> DRIVER = new TypeLiteral<Class<Driver>>() { };
   
    /**
     * Binds {@link JdbcService} to {@link DefaultJdbcService} using
     * the default configuration entries as specified in {@link JdbcConfig}.
     */
    public JdbcServiceModule() {
        
    }
    
    @Override
    public void configure(Binder binder) {
        binder.bind(JdbcService.class).to(DefaultJdbcService.class).in(Singleton.class);
    }

    /**
     * Binds {@link JdbcService} to {@link DefaultJdbcService} annotated with
     * {@link Named} and the specified name.
     * 
     * @param name the name for the service and prefix for the config entries
     * @return Module which rebinds a {@link JdbcService} and the corresponding
     *         configuration entries using the specified name as a prefix
     * @throws NullPointerException if name is null
     */
    public static RebindModule named(String name) {
        Preconditions.checkNotNull(name, "Name");
        return annotatedWith(Names.named(name), name);
    }

    /**
     * Binds {@link JdbcService} to {@link DefaultJdbcService} annotated with
     * the given annotation.
     * 
     * @param annotation the binding annotation for the service
     * @param name the prefix for the config entries
     * @return Module which rebinds a {@link JdbcService} and the corresponding
     *         configuration entries using the specified name as a prefix
     * @throws NullPointerException if name is null
     */
    public static RebindModule annotatedWith(final Annotation annotation, final String name) {
        Preconditions.checkNotNull(annotation, "Annotation");
        Preconditions.checkNotNull(name, "Name");
        return new AnnotatedInstanceModule(annotation, name);
    }
    
    /**
     * Static inner class to provide a module which rebinds using an annotation instance.
     *
     * @author Willi Schoenborn
     */
    private static final class AnnotatedInstanceModule extends AbstractRebindingModule {
        
        private final Annotation annotation;
        private final Config config;
        
        private AnnotatedInstanceModule(Annotation annotation, String name) {
            this.annotation = annotation;
            this.config = new Config(name);
        }

        @Override
        protected void configuration() {
            bind(String.class).annotatedWith(Names.named(JdbcConfig.URL)).to(
                Key.get(String.class, Names.named(config.prefixed(JdbcConfig.URL))));
            bind(DRIVER).annotatedWith(Names.named(JdbcConfig.DRIVER)).to(
                Key.get(DRIVER, Names.named(config.prefixed(JdbcConfig.DRIVER))));
        }
        
        @Override
        protected void optionals() {
            bind(String.class).annotatedWith(Names.named(JdbcConfig.TEST_STATEMENT)).to(
                Key.get(String.class, Names.named(config.prefixed(JdbcConfig.TEST_STATEMENT))));
            
            bind(Properties.class).annotatedWith(Names.named(JdbcConfig.PROPERTIES)).to(
                Key.get(Properties.class, Names.named(config.prefixed(JdbcConfig.PROPERTIES))));
        }
        
        @Override
        protected void bindings() {
            bind(JdbcService.class).annotatedWith(annotation).to(DefaultJdbcService.class).in(Singleton.class);
        }
        
        @Override
        protected void expose() {
            expose(JdbcService.class).annotatedWith(annotation);
        }
        
    }

    /**
     * Binds {@link JdbcService} to {@link DefaultJdbcService} annotated with
     * the specified annotation.
     * 
     * @param annotation the binding annotation for the service
     * @param name the prefix for the config entries
     * @return Module which rebinds a {@link JdbcService} and the corresponding
     *         configuration entries using the specified name as a prefix
     * @throws NullPointerException if name is null
     */
    public static RebindModule annotatedWith(final Class<? extends Annotation> annotation, final String name) {
        Preconditions.checkNotNull(annotation, "Annotation");
        Preconditions.checkNotNull(name, "Name");
        return new AnnotatedModule(annotation, name);
    }

    /**
     * Static inner class to provide a module which rebinds using an annotation type.
     *
     * @author Willi Schoenborn
     */
    private static final class AnnotatedModule extends AbstractRebindingModule {
        
        private final Class<? extends Annotation> annotation;
        private final Config config;
        
        private AnnotatedModule(Class<? extends Annotation> annotation, String name) {
            this.annotation = annotation;
            this.config = new Config(name);
        }

        @Override
        protected void configuration() {
            bind(String.class).annotatedWith(Names.named(JdbcConfig.URL)).to(
                Key.get(String.class, Names.named(config.prefixed(JdbcConfig.URL))));
            bind(DRIVER).annotatedWith(Names.named(JdbcConfig.DRIVER)).to(
                Key.get(DRIVER, Names.named(config.prefixed(JdbcConfig.DRIVER))));
        }
        
        @Override
        protected void optionals() {
            bind(String.class).annotatedWith(Names.named(JdbcConfig.TEST_STATEMENT)).to(
                Key.get(String.class, Names.named(config.prefixed(JdbcConfig.TEST_STATEMENT))));
            
            bind(Properties.class).annotatedWith(Names.named(JdbcConfig.PROPERTIES)).to(
                Key.get(Properties.class, Names.named(config.prefixed(JdbcConfig.PROPERTIES))));
        }
        
        @Override
        protected void bindings() {
            bind(JdbcService.class).annotatedWith(annotation).to(DefaultJdbcService.class).in(Singleton.class);
        }
        
        @Override
        protected void expose() {
            expose(JdbcService.class).annotatedWith(annotation);
        }
        
    }
    
}
