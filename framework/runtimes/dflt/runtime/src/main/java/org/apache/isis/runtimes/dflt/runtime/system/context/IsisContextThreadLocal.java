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

package org.apache.isis.runtimes.dflt.runtime.system.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.commons.debug.DebugBuilder;
import org.apache.isis.runtimes.dflt.runtime.system.session.IsisSession;
import org.apache.isis.runtimes.dflt.runtime.system.session.IsisSessionFactory;
import org.apache.log4j.Logger;

/**
 * Basic multi-user implementation of Isis that stores a set of components for each thread in use.
 */
public class IsisContextThreadLocal extends IsisContextMultiUser {

    private static final Logger LOG = Logger.getLogger(IsisContextThreadLocal.class);

    public static IsisContext createInstance(final IsisSessionFactory sessionFactory) {
        return new IsisContextThreadLocal(sessionFactory);
    }

    private final Map<Thread, IsisSession> sessionsByThread = new HashMap<Thread, IsisSession>();

    protected IsisContextThreadLocal(final IsisSessionFactory sessionFactory) {
        super(sessionFactory);
    }

    // /////////////////////////////////////////////////////////
    // Session
    // /////////////////////////////////////////////////////////

    @Override
    public void closeAllSessionsInstance() {
        shutdownAllThreads();
    }

    protected void shutdownAllThreads() {
        synchronized (sessionsByThread) {
            int i = 0;
            for (final Thread thread : sessionsByThread.keySet()) {
                LOG.info("Shutting down thread: " + i++);
                final IsisSession data = sessionsByThread.get(thread);
                data.closeAll();
            }
        }
    }

    @Override
    protected void doClose() {
        sessionsByThread.remove(Thread.currentThread());
    }

    // /////////////////////////////////////////////////////////
    // Execution Context Ids
    // /////////////////////////////////////////////////////////

    @Override
    public String[] allSessionIds() {
        final String[] ids = new String[sessionsByThread.size()];
        int i = 0;
        for (final Thread thread : sessionsByThread.keySet()) {
            final IsisSession data = sessionsByThread.get(thread);
            ids[i++] = data.getId();
        }
        return ids;
    }

    // /////////////////////////////////////////////////////////
    // Debugging
    // /////////////////////////////////////////////////////////

    @Override
    public String debugTitle() {
        return "Isis (by thread) " + Thread.currentThread().getName();
    }

    @Override
    public void debugData(final DebugBuilder debug) {
        super.debugData(debug);
        debug.appendln();
        debug.appendTitle("Threads based Contexts");
        for (final Thread thread : sessionsByThread.keySet()) {
            final IsisSession data = sessionsByThread.get(thread);
            debug.appendln(thread.toString(), data);
        }
    }

    @Override
    protected IsisSession getSessionInstance(final String executionContextId) {
        for (final Thread thread : sessionsByThread.keySet()) {
            final IsisSession data = sessionsByThread.get(thread);
            if (data.getId().equals(executionContextId)) {
                return data;
            }
        }
        return null;
    }

    // /////////////////////////////////////////////////////////
    // open, close
    // /////////////////////////////////////////////////////////

    /**
     * Is only intended to be called through {@link IsisContext#openSession(AuthenticationSession)}.
     * 
     * <p>
     * Implementation note: an alternative design would have just been to bind onto a thread local.
     */
    @Override
    public IsisSession openSessionInstance(final AuthenticationSession authenticationSession) {
        final Thread thread = Thread.currentThread();
        synchronized (sessionsByThread) {
            applySessionClosePolicy();
            final IsisSession session = getSessionFactoryInstance().openSession(authenticationSession);
            LOG.debug("  opening session " + session + " (count " + sessionsByThread.size() + ") for "
                + authenticationSession.getUserName());
            saveSession(thread, session);
            session.open();
            return session;
        }
    }

    protected IsisSession createAndOpenSession(final Thread thread, final AuthenticationSession authenticationSession) {
        final IsisSession session = getSessionFactoryInstance().openSession(authenticationSession);
        session.open();
        LOG.info("  opening session " + session + " (count " + sessionsByThread.size() + ") for "
            + authenticationSession.getUserName());
        return session;
    }

    private IsisSession saveSession(final Thread thread, final IsisSession session) {
        synchronized (sessionsByThread) {
            sessionsByThread.put(thread, session);
        }
        LOG.debug("  saving session " + session + "; now have " + sessionsByThread.size() + " sessions");
        return session;
    }

    // /////////////////////////////////////////////////////////
    // getCurrent() (Hook)
    // /////////////////////////////////////////////////////////

    /**
     * Get {@link IsisSession execution context} used by the current thread.
     * 
     * @see #openSessionInstance(AuthenticationSession)
     */
    @Override
    public IsisSession getSessionInstance() {
        final Thread thread = Thread.currentThread();
        final IsisSession session = sessionsByThread.get(thread);
        return session;
    }

}
