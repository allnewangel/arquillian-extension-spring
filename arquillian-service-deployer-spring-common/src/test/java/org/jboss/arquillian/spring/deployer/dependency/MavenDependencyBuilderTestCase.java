/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.spring.deployer.dependency;

import org.jboss.shrinkwrap.resolver.api.ResolutionException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * <p>Tests {@link MavenDependencyBuilder} class.</p>
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 */
public class MavenDependencyBuilderTestCase {

    /**
     * <p>Represents the instance of tested class.</p>
     */
    private MavenDependencyBuilder instance;

    /**
     * <p>Sets up the test environment.</p>
     */
    @Before
    public void setUp() {

        instance = new MavenDependencyBuilder();
    }

    /**
     * <p>Tests the {@link org.jboss.arquillian.spring.deployer.dependency.MavenDependencyBuilder#getDependencies()}
     * method.</p>
     *
     * <p>{@link ResolutionException} is expected.</p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetDependencies() {

        File[] result = instance.getDependencies();

        assertNotNull("Invalid value was returned.", result);
        assertEquals("Incorrect number of dependencies, none was expected.", 0, result.length);
    }

    /**
     * <p>Tests the {@link org.jboss.arquillian.spring.deployer.dependency.MavenDependencyBuilder#addDependency(String,
     * String, String...)} method.</p>
     */
    @Test
    public void testAddDependenciesDefault() {

        instance.addDependency("org.springframework:spring-context", "3.0.0.RELEASE");

        File[] result = instance.getDependencies();

        assertNotNull("Invalid value was returned.", result);
        assertDependencyExists(result, "spring-context", "3.0.0.RELEASE");
    }

    /**
     * <p>Tests the {@link org.jboss.arquillian.spring.deployer.dependency.MavenDependencyBuilder#addDependency(String,
     * String, String...)} method.</p>
     */
    @Test
    public void testAddDependencies() {

        instance.addDependency("org.springframework:spring-context", "3.1.1.RELEASE");

        File[] result = instance.getDependencies();

        assertNotNull("Invalid value was returned.", result);
        assertDependencyExists(result, "spring-context", "3.1.1.RELEASE");
    }

    /**
     * <p>Tests the {@link org.jboss.arquillian.spring.deployer.dependency.MavenDependencyBuilder#addDependency(String,
     * String, String...)} method.</p>
     *
     * {@link ResolutionException} is expected
     */
    @Test(expected = ResolutionException.class)
    public void testAddDependenciesError() {

        instance.addDependency("org.springframework:spring-context", "3");

        instance.getDependencies();
    }

    /**
     * <p>Checks whether the resolved dependencies contains required artifact.</p>
     *
     * @param files        the dependencies files
     * @param artifactName the artifact name
     * @param version      the artifact version
     */
    private void assertDependencyExists(File[] files, String artifactName, String version) {

        boolean dependencyExists = false;
        String fileName = String.format("%s-%s.jar", artifactName, version);

        for (File file : files) {

            if (file.getName().equals(fileName)) {
                dependencyExists = true;
                break;
            }
        }

        assertTrue("The required dependency " + fileName + " is missing.", dependencyExists);
    }
}
