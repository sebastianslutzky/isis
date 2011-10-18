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

package org.apache.isis.runtimes.dflt.remoting.common.data;

import org.apache.isis.core.commons.lang.ToString;
import org.apache.isis.runtimes.dflt.remoting.common.data.common.EncodableObjectData;

public final class DummyEncodeableObjectData implements EncodableObjectData {
    private static final long serialVersionUID = 1L;

    public String value;
    public String type;

    public DummyEncodeableObjectData(final String value) {
        this(value, String.class.getName());
    }

    public DummyEncodeableObjectData(final String value, final String type) {
        super();
        this.value = value;
        this.type = type;
    }

    @Override
    public String getEncodedObjectData() {
        return value;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DummyEncodeableObjectData other = (DummyEncodeableObjectData) obj;
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final ToString str = new ToString(this);
        str.append("type", type);
        str.append("value", value);
        return str.toString();
    }

}