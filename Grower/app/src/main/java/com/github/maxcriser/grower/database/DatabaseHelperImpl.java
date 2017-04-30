package com.github.maxcriser.grower.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.maxcriser.grower.async.OnResultCallback;
import com.github.maxcriser.grower.database.annotations.Table;
import com.github.maxcriser.grower.database.annotations.typeBlob;
import com.github.maxcriser.grower.database.annotations.typeBoolean;
import com.github.maxcriser.grower.database.annotations.typeFloat;
import com.github.maxcriser.grower.database.annotations.typeInteger;
import com.github.maxcriser.grower.database.annotations.typePrimaryKey;
import com.github.maxcriser.grower.database.annotations.typeString;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Locale;

import static com.github.maxcriser.grower.constant.ListConstants.Database.SQL_TABLE_CREATE_FIELD_TEMPLATE;
import static com.github.maxcriser.grower.constant.ListConstants.Database.SQL_TABLE_CREATE_TEMPLATE;
import static com.github.maxcriser.grower.constant.ListConstants.Database.dbVersion;
import static com.github.maxcriser.grower.constant.ListConstants.Database.mDatabaseName;

public final class DatabaseHelperImpl extends SQLiteOpenHelper implements DatabaseHelper {

    DatabaseHelperImpl(final Context pContext) {
        super(pContext, mDatabaseName, null, dbVersion);
//        ContextHolder.get().deleteDatabase(mDatabaseName);
        getWritableDatabase();
    }

    @Nullable
    static String getTableName(final AnnotatedElement pModel) {
        final Table table = pModel.getAnnotation(Table.class);
        if (table != null) {
            return table.name();
        } else {
            return null;
        }
    }

    @Nullable
    private static String getTableCreateQuery(final Class<?> pModel) {
        final Table table = pModel.getAnnotation(Table.class);
        if (table != null) {
            try {
                final String name = table.name();
                final StringBuilder builder = new StringBuilder();
                final Field[] fields = pModel.getFields();
                for (int i = 0; i < fields.length; i++) {
                    final Annotation[] annotations = fields[i].getAnnotations();
                    String type = null;
                    String additionalKeys = "";
                    for (final Annotation annotation : annotations) {
                        if (annotation instanceof typeInteger) {
                            type = ((typeInteger) annotation).value();
                        } else if (annotation instanceof typeString) {
                            type = ((typeString) annotation).value();
                        } else if (annotation instanceof typeFloat) {
                            type = ((typeFloat) annotation).value();
                        } else if (annotation instanceof typeBoolean) {
                            type = ((typeBoolean) annotation).value();
                        } else if (annotation instanceof typeBlob) {
                            type = ((typeBlob) annotation).value();
                        } else if (annotation instanceof typePrimaryKey) {
                            additionalKeys = " " + ((typePrimaryKey) annotation).value();
                        }
                    }
                    if (type != null) {
                        final String value = (String) fields[i].get(null);
                        builder.append(String.format(Locale.US, SQL_TABLE_CREATE_FIELD_TEMPLATE,
                                value, type, additionalKeys));
                        if (i < fields.length - 2) {
                            builder.append(",");
                        }
                    }
                }
                return String.format(Locale.US, SQL_TABLE_CREATE_TEMPLATE,
                        name, builder.toString());
            } catch (final Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void onCreate(final SQLiteDatabase pDatabase) {
        for (final Class<?> clazz : ModelList.MODELS) {
            final String sql = getTableCreateQuery(clazz);
            if (sql != null) {
                pDatabase.execSQL(sql);
            }
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase pDatabase,
                          final int pOldVersion,
                          final int pNewVersion) {

    }

    public synchronized void query(@NonNull final OnResultCallback<Cursor, Void> pCallback,
                                   final String pSqlQuery,
                                   final AnnotatedElement pModel,
                                   final String pSqlCondition,
                                   final String... pArgs) {
        new AsyncTask<Void, Void, Cursor>() {

            @Override
            protected Cursor doInBackground(final Void... params) {
                final SQLiteDatabase database = getReadableDatabase();
                final String sql = "SELECT " + pSqlQuery + " FROM " + getTableName(pModel) + " " + pSqlCondition;
                return database.rawQuery(sql, pArgs);
            }

            @Override
            protected void onPostExecute(final Cursor pCursor) {
                pCallback.onSuccess(pCursor);
            }
        }.execute();
    }

    public synchronized void insert(final AnnotatedElement pModel,
                                    final ContentValues pValues,
                                    @Nullable final OnResultCallback<Long, Void> pCallback) {
        new AsyncTask<Void, Void, Long>() {

            @Override
            protected Long doInBackground(final Void... params) {
                final String name = getTableName(pModel);
                if (name != null) {
                    final SQLiteDatabase database = getWritableDatabase();
                    long id;
                    try {
                        database.beginTransaction();
                        id = database.insert(name, null, pValues);
                        database.setTransactionSuccessful();
                    } finally {
                        database.endTransaction();
                    }
                    return id;
                } else {
                    final RuntimeException exception = new RuntimeException("No such table exists");
                    if (pCallback != null) {
                        pCallback.onError(exception);
                    } else {
                        throw exception;
                    }
                    return -1L;
                }
            }

            @Override
            protected void onPostExecute(final Long pLong) {
                if (pCallback != null) {
                    pCallback.onSuccess(pLong);
                }
            }
        }.execute();
    }

    public synchronized void bulkInsert(final AnnotatedElement pModel,
                                        final Iterable<ContentValues> pValuesList,
                                        @Nullable final OnResultCallback<Integer, Void> pCallback) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(final Void... params) {
                final String name = getTableName(pModel);
                if (name != null) {
                    final SQLiteDatabase database = getWritableDatabase();
                    int count = 0;
                    try {
                        database.beginTransaction();
                        for (final ContentValues value : pValuesList) {
                            database.insert(name, null, value);
                            count++;
                        }
                        database.setTransactionSuccessful();
                    } finally {
                        database.endTransaction();
                    }
                    return count;
                } else {
                    final RuntimeException exception = new RuntimeException("No such table exists");
                    if (pCallback != null) {
                        pCallback.onError(exception);
                    } else {
                        throw exception;
                    }
                    return -1;
                }
            }

            @Override
            protected void onPostExecute(final Integer pInteger) {
                if (pCallback != null) {
                    pCallback.onSuccess(pInteger);
                }
            }
        }.execute();
    }

    public synchronized void edit(final AnnotatedElement pModel,
                                  final String column,
                                  final String newValue,
                                  final String searchColumn,
                                  final String valueOfSearchColumn, @Nullable final OnResultCallback<Void, Void> pCallback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(final Void... params) {
                final SQLiteDatabase database = getReadableDatabase();
                final String sql = "UPDATE " + getTableName(pModel) + " SET " + column + " = '" + newValue + "' WHERE " + searchColumn + " = " + valueOfSearchColumn;
                database.execSQL(sql);

                return null;
            }

            @Override
            protected void onPostExecute(final Void pVoid) {
                if (pCallback != null) {
                    pCallback.onSuccess(pVoid);
                }
            }
        }.execute();
    }

    public synchronized void delete(final AnnotatedElement pModel,
                                    @Nullable final OnResultCallback<Integer, Void> pCallback,
                                    final String pSql,
                                    final String... pArgs) {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(final Void... params) {
                final String name = getTableName(pModel);
                if (name != null) {
                    final SQLiteDatabase database = getWritableDatabase();
                    int count = 0;
                    try {
                        database.beginTransaction();
                        count = database.delete(name, pSql, pArgs);
                        database.setTransactionSuccessful();
                    } finally {
                        database.endTransaction();
                    }
                    return count;
                } else {
                    final RuntimeException exception = new RuntimeException("No such table exists");
                    if (pCallback != null) {
                        pCallback.onError(exception);
                    } else {
                        throw exception;
                    }
                    return -1;
                }
            }

            @Override
            protected void onPostExecute(final Integer pInteger) {
                if (pCallback != null) {
                    pCallback.onSuccess(pInteger);
                }
            }
        }.execute();
    }
}