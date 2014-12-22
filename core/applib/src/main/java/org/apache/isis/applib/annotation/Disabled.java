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

package org.apache.isis.applib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @deprecated - see {@link Property#disabled()}, {@link Collection#disabled()} and {@link Action#disabled()}.
 */
@Inherited
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface Disabled {

    /**
     * @deprecated.  There is no corresponding attribute in {@link org.apache.isis.applib.annotation.Property}, {@link org.apache.isis.applib.annotation.Collection} and {@link org.apache.isis.applib.annotation.Action}, if the
     * corresponding <code>disabled()</code> attribute is set then the feature is assumed to be disabled ALWAYS.
     */
    @Deprecated
    When when() default When.ALWAYS;

    /**
     * @deprecated - see {@link Property#disabled()}, {@link Collection#disabled()} and {@link Action#disabled()}.
     */
    @Deprecated
    Where where() default Where.ANYWHERE;

    @Deprecated
    String reason() default "";
}
