/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package webapp.background;

import java.util.List;

import dom.todo.ToDoItem;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.services.background.BackgroundService;
import org.apache.isis.objectstore.jdo.applib.service.background.BackgroundTaskJdo;
import org.apache.isis.objectstore.jdo.applib.service.background.BackgroundTaskServiceJdo;

/**
 * Defines a new top-level menu
 */
@Named("Background Tasks")
public class BackgroundTasks extends BackgroundTaskServiceJdo {

    @MemberOrder(sequence="20")
    @Override
    public List<BackgroundTaskJdo> allTasks() {
        return super.allTasks();
    }
    
}
