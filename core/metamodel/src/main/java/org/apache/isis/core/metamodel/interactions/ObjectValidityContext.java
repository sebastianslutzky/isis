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

package org.apache.isis.core.metamodel.interactions;

import static org.apache.isis.core.metamodel.adapter.ObjectAdapter.Util.unwrap;


import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.events.ObjectValidityEvent;
import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.consent.InteractionContextType;
import org.apache.isis.core.metamodel.consent.InteractionInvocationMethod;
import org.apache.isis.core.metamodel.deployment.DeploymentCategory;

/**
 * See {@link InteractionContext} for overview; analogous to
 * {@link ObjectValidityEvent}.
 */
public class ObjectValidityContext extends ValidityContext<ObjectValidityEvent> implements ProposedHolder {

    public ObjectValidityContext(DeploymentCategory deploymentCategory, final AuthenticationSession session, final InteractionInvocationMethod invocationMethod, final ObjectAdapter target, final Identifier identifier) {
        super(InteractionContextType.OBJECT_VALIDATE, deploymentCategory, session, invocationMethod, identifier, target);
    }

    @Override
    public ObjectValidityEvent createInteractionEvent() {
        return new ObjectValidityEvent(unwrap(getTarget()), getIdentifier());
    }

    @Override
    public ObjectAdapter getProposed() {
        return getTarget();
    }

}
