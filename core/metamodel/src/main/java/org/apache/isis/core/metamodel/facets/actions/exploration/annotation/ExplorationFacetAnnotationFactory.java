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

package org.apache.isis.core.metamodel.facets.actions.exploration.annotation;

import org.apache.isis.applib.annotation.Exploration;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facetapi.FacetUtil;
import org.apache.isis.core.metamodel.facetapi.FeatureType;
import org.apache.isis.core.metamodel.facets.Annotations;
import org.apache.isis.core.metamodel.facets.FacetFactoryAbstract;
import org.apache.isis.core.metamodel.facets.actions.exploration.ExplorationFacet;

/**
 * {@link ExplorationFacet} can also be installed via a naming convention, see
 * {@link org.apache.isis.core.metamodel.facets.actions.interaction.ActionInteractionFacetFactory}.
 */
public class ExplorationFacetAnnotationFactory extends FacetFactoryAbstract {

    public ExplorationFacetAnnotationFactory() {
        super(FeatureType.ACTIONS_ONLY);
    }

    @Override
    public void process(final ProcessMethodContext processMethodContext) {
        final Exploration annotation = Annotations.getAnnotation(processMethodContext.getMethod(), Exploration.class);
        FacetUtil.addFacet(create(annotation, processMethodContext.getFacetHolder()));
    }

    private ExplorationFacet create(final Exploration annotation, final FacetHolder holder) {
        return annotation == null ? null : new ExplorationFacetAnnotation(holder);
    }

}
