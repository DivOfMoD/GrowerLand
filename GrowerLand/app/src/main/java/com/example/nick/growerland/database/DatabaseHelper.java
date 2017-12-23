package com.example.nick.growerland.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.nick.growerland.async.OnResultCallback;

import java.lang.reflect.AnnotatedElement;

public interface DatabaseHelper {

    void query(@NonNull final OnResultCallback<Cursor, Void> pCallback,
               final String pSqlQuery,
               final AnnotatedElement pModel,
               final String pSqlCondition,
               final String... pArgs);

    void insert(final AnnotatedElement pModel,
                final ContentValues pValues,
                @Nullable final OnResultCallback<Long, Void> pCallback);

    void bulkInsert(final AnnotatedElement pModel,
                    final Iterable<ContentValues> pValuesList,
                    @Nullable final OnResultCallback<Integer, Void> pCallback);

    void edit(final AnnotatedElement pModel,
              final String column,
              final String newValue,
              final String searchColumn,
              final String valueOfSearchColumn, @Nullable final OnResultCallback<Void, Void> pCallback);

    void delete(final AnnotatedElement pModel,
                @Nullable final OnResultCallback<Integer, Void> pCallback,
                final String pSql,
                final String... pArgs);

    SQLiteDatabase getReadableDatabase();

    final class Impl {

        public static DatabaseHelper newInstance(final Context pContext) {
            return new DatabaseHelperImpl(pContext);
        }
    }

}
