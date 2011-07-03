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

package org.apache.isis.runtimes.dflt.remoting.common.data.common;

import java.io.IOException;
import java.io.Serializable;

import org.apache.isis.core.commons.encoding.DataInputExtended;
import org.apache.isis.core.commons.encoding.DataOutputExtended;
import org.apache.isis.core.commons.encoding.Encodable;
import org.apache.isis.core.commons.lang.ToString;

public class NullDataImpl implements NullData, Encodable, Serializable {

    private static final long serialVersionUID = 1L;
    private final String type;

    public NullDataImpl(final String type) {
        this.type = type;
        initialized();
    }

    public NullDataImpl(final DataInputExtended input) throws IOException {
        this.type = input.readUTF();
        initialized();
    }

    @Override
    public void encode(final DataOutputExtended output) throws IOException {
        output.writeUTF(type);
    }

    private void initialized() {
        // nothing to do
    }

    // ///////////////////////////////////////////////////////
    //
    // ///////////////////////////////////////////////////////

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        final ToString str = new ToString(this);
        str.append("type", type);
        return str.toString();
    }
}
