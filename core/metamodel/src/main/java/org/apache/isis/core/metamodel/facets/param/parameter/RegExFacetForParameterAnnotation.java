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

package org.apache.isis.core.metamodel.facets.param.parameter;

import java.util.regex.Pattern;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.object.regex.RegExFacet;
import org.apache.isis.core.metamodel.facets.object.regex.RegExFacetAbstract;

public class RegExFacetForParameterAnnotation extends RegExFacetAbstract {

    private final Pattern pattern;

    static RegExFacet create(final Parameter parameter, final FacetHolder holder) {

        final String pattern = parameter.regexPattern();
        final int patternFlags = parameter.regexPatternFlags();

        return new RegExFacetForParameterAnnotation(pattern, patternFlags, holder);
    }

    private RegExFacetForParameterAnnotation(final String pattern, final int patternFlags, final FacetHolder holder) {
        super(pattern, "", false, holder);
        this.pattern = Pattern.compile(pattern, patternFlags);
    }

    /**
     * Unused (for the TitledFacet)
     */
    @Override
    public String format(String text) {
        return text;
    }

    @Override
    public boolean doesNotMatch(final String text) {
        if (text == null) {
            return true;
        }
        return !pattern.matcher(text).matches();
    }


}
