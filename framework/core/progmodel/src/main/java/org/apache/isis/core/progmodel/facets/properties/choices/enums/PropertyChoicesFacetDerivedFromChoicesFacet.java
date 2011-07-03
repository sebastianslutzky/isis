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

package org.apache.isis.core.progmodel.facets.properties.choices.enums;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.FacetedMethod;
import org.apache.isis.core.metamodel.facets.choices.ChoicesFacet;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.SpecificationLookup;
import org.apache.isis.core.progmodel.facets.properties.choices.PropertyChoicesFacetAbstract;

public class PropertyChoicesFacetDerivedFromChoicesFacet extends PropertyChoicesFacetAbstract {

    public PropertyChoicesFacetDerivedFromChoicesFacet(final FacetHolder holder,
        final SpecificationLookup specificationLookup) {
        super(holder, specificationLookup);
    }

    @Override
    public Object[] getChoices(final ObjectAdapter adapter, final SpecificationLookup specificationLookup) {
        final FacetHolder facetHolder = getFacetHolder();
        final FacetedMethod noap = (FacetedMethod) facetHolder;
        final ObjectSpecification noSpec = getSpecification(noap.getType());
        final ChoicesFacet choicesFacet = noSpec.getFacet(ChoicesFacet.class);
        if (choicesFacet == null) {
            return new Object[0];
        }
        return choicesFacet.getChoices(adapter);
    }

    public ObjectSpecification getSpecification(final Class<?> type) {
        return type == null ? null : getSpecificationLoader().loadSpecification(type);
    }

}
