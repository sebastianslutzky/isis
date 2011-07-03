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

package org.apache.isis.viewer.wicket.ui.components.widgets.formcomponent;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * {@link FeedbackPanel} designed for forms; filters out any {@link FeedbackMessage}s from {@link FormComponent}s (the
 * idea being that they will have their own {@link FeedbackPanel}s.
 */
public class FormFeedbackPanel extends FeedbackPanel {
    private static final long serialVersionUID = 1L;

    public FormFeedbackPanel(final String id) {
        super(id);
        setFilter(new IFeedbackMessageFilter() {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean accept(final FeedbackMessage message) {
                return !(message.getReporter() instanceof FormComponent<?>);
            }
        });
    }
}