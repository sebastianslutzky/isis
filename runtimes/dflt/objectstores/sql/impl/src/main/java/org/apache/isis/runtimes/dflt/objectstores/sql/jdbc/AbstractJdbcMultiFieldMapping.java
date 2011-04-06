package org.apache.isis.runtimes.dflt.objectstores.sql.jdbc;

import org.apache.isis.applib.ApplicationException;
import org.apache.isis.core.commons.debug.DebugBuilder;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.core.metamodel.spec.feature.OneToOneAssociation;
import org.apache.isis.runtimes.dflt.objectstores.sql.DatabaseConnector;
import org.apache.isis.runtimes.dflt.objectstores.sql.Results;
import org.apache.isis.runtimes.dflt.objectstores.sql.Sql;
import org.apache.isis.runtimes.dflt.runtime.context.IsisContext;

public abstract class AbstractJdbcMultiFieldMapping extends AbstractJdbcFieldMapping {
    private final int columnCount;
    private final String[] types;
    private final String[] columnNames;

    /**
     * 
     * @param field
     *            the field object association.
     * @param columnCount
     *            the number of columns required to store this field. See the abstract methods ,
     *            {@link AbstractJdbcFieldMapping#preparedStatementObject(int i, ObjectAdapter fieldValue)},
     *            {@link AbstractJdbcFieldMapping#getObjectFromResults(Results results)},
     * @param types
     *            the list of SQL data types, 1 per columnCount, to represent the value type.
     */
    public AbstractJdbcMultiFieldMapping(ObjectAssociation field, int columnCount, String... types) {
        super(field);
        this.columnCount = columnCount;

        this.types = new String[columnCount];
        for (int i = 0; i < types.length; i++) {
            this.types[i] = types[i];
        }

        String fieldName = field.getId();
        columnNames = new String[columnCount];
        columnNames[0] = Sql.sqlFieldName(fieldName + "1");
        columnNames[1] = Sql.sqlFieldName(fieldName + "2");
    }

    @Override
    public void appendColumnDefinitions(StringBuffer sql) {
        for (int i = 0; i < columnCount; i++) {
            sql.append(columnName(i) + " " + columnType(i));
            if (i < columnCount - 1) {
                sql.append(", ");
            }
        }
    }

    @Override
    public void appendColumnNames(StringBuffer sql) {
        for (int i = 0; i < columnCount; i++) {
            sql.append(columnName(i));
            if (i < columnCount - 1) {
                sql.append(", ");
            }
        }
    }

    @Override
    public void appendInsertValues(DatabaseConnector connector, StringBuffer sql, ObjectAdapter object) {
        ObjectAdapter fieldValue = field.get(object);
        Object o = (fieldValue == null) ? null : fieldValue.getObject();

        for (int i = 0; i < columnCount; i++) {
            if (fieldValue == null) {
                sql.append("NULL");
            } else {
                sql.append("?");
                if (i < columnCount - 1) {
                    sql.append(", ");
                }

                connector.addToQueryValues(preparedStatementObject(i, o));
            }
        }
    }

    @Override
    public void appendUpdateValues(DatabaseConnector connector, StringBuffer sql, ObjectAdapter object) {
        for (int i = 0; i < columnCount; i++) {
            appendEqualsClause(connector, i, sql, object, "=");
        }
    }

    @Override
    public void appendWhereClause(DatabaseConnector connector, StringBuffer sql, ObjectAdapter object) {
        for (int i = 0; i < columnCount; i++) {
            appendEqualsClause(connector, i, sql, object, "=");
        }
    }

    protected void appendEqualsClause(DatabaseConnector connector, int index, StringBuffer sql, ObjectAdapter object,
        String condition) {

        ObjectAdapter fieldValue = field.get(object);
        Object o = (fieldValue == null) ? null : fieldValue.getObject();

        for (int i = 0; i < columnCount; i++) {
            sql.append(columnName(i) + condition + "?");
            if (i < columnCount - 1) {
                sql.append(", ");
            }

            connector.addToQueryValues(o);
        }
    }

    @Override
    public void initializeField(ObjectAdapter object, Results rs) {
        // TODO: remove this definition when encodedValue is no longer passed to setFromDBColumn
        String columnName = columnName(0);
        String encodedValue = rs.getString(columnName);

        ObjectAdapter restoredValue;
        if (encodedValue == null) {
            restoredValue = null;
        } else {
            restoredValue = setFromDBColumn(rs, encodedValue, columnName, field);

        }
        ((OneToOneAssociation) field).initAssociation(object, restoredValue);
    }

    @Override
    public ObjectAdapter setFromDBColumn(Results results, final String encodedValue, String columnName,
        final ObjectAssociation field) {

        ObjectAdapter restoredValue;
        Object objectValue = getObjectFromResults(results);
        restoredValue = IsisContext.getPersistenceSession().getAdapterManager().adapterFor(objectValue);

        return restoredValue;

    }

    @Override
    public void debugData(DebugBuilder debug) {
        for (int i = 0; i < columnCount; i++) {
            debug.appendln(field.getId(), columnName(i) + "/" + columnType(i));
        }
    }

    @Override
    protected String columnType() {
        throw new ApplicationException("Should never be called");
    }

    @Override
    protected Object preparedStatementObject(ObjectAdapter value) {
        throw new ApplicationException("Should never be called");
    }

    protected String columnType(int index) {
        return types[index];
    }

    protected String columnName(int index) {
        return columnNames[index];
    }

    /**
     * Return an object suitable for passing to the SQL prepared statement constructor, to handle field "index". Will be
     * called "columnCount" times.
     * 
     * @param index
     *            0 based index
     * @param fieldObject
     *            the value type currently being
     * @return a JDBC-compatible object.
     */
    protected abstract Object preparedStatementObject(int index, Object o);

    /**
     * Return an applib object represented by the results set.
     * 
     * @param results
     *            the current record row from the underlying table
     * @return a fully initialised value object.
     */
    protected abstract Object getObjectFromResults(Results results);
}
