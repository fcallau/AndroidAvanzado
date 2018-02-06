package com.keepcoding.madridshops.repository.db.dao

import android.database.Cursor

interface DAOReadOperations<T> {
    fun query(id: Long): T
    fun query(): List<T>
    fun queryCursor(id: Long): Cursor
}

interface DAOWriteOperations<T> {
    fun insert(element: T): Long
    fun update(id: Long, element: T): Long

    /**
     * deletes the element passed from DB
     */
    fun delete(element: T): Long

    /**
     * deletes the element with id from DB
     */
    fun delete(id: Long): Long
    fun deleteAll(): Boolean
}

interface DAOPersistable<T>: DAOReadOperations<T>, DAOWriteOperations<T>

