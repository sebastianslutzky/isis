/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.objectstore.xml;

import static org.junit.Assert.assertTrue;

import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.runtime.system.DeploymentType;
import org.apache.isis.core.runtime.system.persistence.PersistenceSessionFactory;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

public class XmlPersistenceMechanismInstallerTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);


    private DeploymentType deploymentType;
    XmlPersistenceMechanismInstaller installer;
    
    @Mock
    private IsisConfiguration mockConfiguration;

    @Before
    public void setUpSystem() throws Exception {
        installer = new XmlPersistenceMechanismInstaller();
        installer.setConfiguration(mockConfiguration);
    }

    @Test
    public void testCreatePersistenceSessionFactory() throws Exception {
        deploymentType = DeploymentType.EXPLORATION;
        final PersistenceSessionFactory factory = installer.createPersistenceSessionFactory(deploymentType);
        assertTrue(factory != null);
    }

}
