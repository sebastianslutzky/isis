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

package org.apache.isis.viewer.scimpi.dispatcher.view.value;

import java.util.List;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.ObjectActionParameter;
import org.apache.isis.viewer.scimpi.dispatcher.AbstractElementProcessor;
import org.apache.isis.viewer.scimpi.dispatcher.ScimpiException;
import org.apache.isis.viewer.scimpi.dispatcher.processor.Request;
import org.apache.isis.viewer.scimpi.dispatcher.util.MethodsUtils;

public class ParameterName extends AbstractElementProcessor {

    @Override
    public void process(final Request request) {
        final String objectId = request.getOptionalProperty(OBJECT);
        final String methodName = request.getRequiredProperty(METHOD);
        final String field = request.getOptionalProperty(PARAMETER_NUMBER);

        final ObjectAdapter object = MethodsUtils.findObject(request.getContext(), objectId);
        final ObjectAction action = MethodsUtils.findAction(object, methodName);
        final List<ObjectActionParameter> parameters = action.getParameters();

        int index;
        if (field == null) {
            index = 0;
        } else {
            index = Integer.valueOf(field).intValue() - 1;
        }
        if (index < 0 || index >= parameters.size()) {
            throw new ScimpiException("Parameter numbers should be between 1 and " + parameters.size() + ": " + index);
        }

        request.appendAsHtmlEncoded(parameters.get(index).getName());
    }

    @Override
    public String getName() {
        return "parameter-name";
    }

}
