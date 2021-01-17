package com.example.android.SemanticApp;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class HolidayDao_Impl implements HolidayDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfHoliday;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfHoliday;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfHoliday;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public HolidayDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHoliday = new EntityInsertionAdapter<Holiday>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `holiday_table`(`id`,`holiday`,`product`,`notes`,`opinion`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Holiday value) {
        stmt.bindLong(1, value.getId());
        if (value.getHoliday() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getHoliday());
        }
        if (value.getProduct() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getProduct());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNotes());
        }
        if (value.getOpinion() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getOpinion());
        }
      }
    };
    this.__deletionAdapterOfHoliday = new EntityDeletionOrUpdateAdapter<Holiday>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `holiday_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Holiday value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfHoliday = new EntityDeletionOrUpdateAdapter<Holiday>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `holiday_table` SET `id` = ?,`holiday` = ?,`product` = ?,`notes` = ?,`opinion` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Holiday value) {
        stmt.bindLong(1, value.getId());
        if (value.getHoliday() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getHoliday());
        }
        if (value.getProduct() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getProduct());
        }
        if (value.getNotes() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNotes());
        }
        if (value.getOpinion() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getOpinion());
        }
        stmt.bindLong(6, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM holiday_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(Holiday holiday) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfHoliday.insert(holiday);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Holiday holiday) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfHoliday.handle(holiday);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Holiday... holiday) {
    __db.beginTransaction();
    try {
      __updateAdapterOfHoliday.handleMultiple(holiday);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public Holiday[] getAnyHoliday() {
    final String _sql = "SELECT * from holiday_table LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMHoliday = _cursor.getColumnIndexOrThrow("holiday");
      final int _cursorIndexOfMProduct = _cursor.getColumnIndexOrThrow("product");
      final int _cursorIndexOfMNotes = _cursor.getColumnIndexOrThrow("notes");
      final int _cursorIndexOfMOpinion = _cursor.getColumnIndexOrThrow("opinion");
      final Holiday[] _result = new Holiday[_cursor.getCount()];
      int _index = 0;
      while(_cursor.moveToNext()) {
        final Holiday _item;
        final String _tmpMHoliday;
        _tmpMHoliday = _cursor.getString(_cursorIndexOfMHoliday);
        _item = new Holiday(_tmpMHoliday);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpMProduct;
        _tmpMProduct = _cursor.getString(_cursorIndexOfMProduct);
        _item.setProduct(_tmpMProduct);
        final String _tmpMNotes;
        _tmpMNotes = _cursor.getString(_cursorIndexOfMNotes);
        _item.setNotes(_tmpMNotes);
        final String _tmpMOpinion;
        _tmpMOpinion = _cursor.getString(_cursorIndexOfMOpinion);
        _item.setOpinion(_tmpMOpinion);
        _result[_index] = _item;
        _index ++;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Holiday>> getAllHolidays() {
    final String _sql = "SELECT * from holiday_table ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Holiday>>() {
      private Observer _observer;

      @Override
      protected List<Holiday> compute() {
        if (_observer == null) {
          _observer = new Observer("holiday_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfMHoliday = _cursor.getColumnIndexOrThrow("holiday");
          final int _cursorIndexOfMProduct = _cursor.getColumnIndexOrThrow("product");
          final int _cursorIndexOfMNotes = _cursor.getColumnIndexOrThrow("notes");
          final int _cursorIndexOfMOpinion = _cursor.getColumnIndexOrThrow("opinion");
          final List<Holiday> _result = new ArrayList<Holiday>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Holiday _item;
            final String _tmpMHoliday;
            _tmpMHoliday = _cursor.getString(_cursorIndexOfMHoliday);
            _item = new Holiday(_tmpMHoliday);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpMProduct;
            _tmpMProduct = _cursor.getString(_cursorIndexOfMProduct);
            _item.setProduct(_tmpMProduct);
            final String _tmpMNotes;
            _tmpMNotes = _cursor.getString(_cursorIndexOfMNotes);
            _item.setNotes(_tmpMNotes);
            final String _tmpMOpinion;
            _tmpMOpinion = _cursor.getString(_cursorIndexOfMOpinion);
            _item.setOpinion(_tmpMOpinion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<Holiday>> getBBHolidays() {
    final String _sql = "SELECT * from holiday_table where product = \"BBGUNS4LESS\" ORDER BY id DESC ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Holiday>>() {
      private Observer _observer;

      @Override
      protected List<Holiday> compute() {
        if (_observer == null) {
          _observer = new Observer("holiday_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfMHoliday = _cursor.getColumnIndexOrThrow("holiday");
          final int _cursorIndexOfMProduct = _cursor.getColumnIndexOrThrow("product");
          final int _cursorIndexOfMNotes = _cursor.getColumnIndexOrThrow("notes");
          final int _cursorIndexOfMOpinion = _cursor.getColumnIndexOrThrow("opinion");
          final List<Holiday> _result = new ArrayList<Holiday>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Holiday _item;
            final String _tmpMHoliday;
            _tmpMHoliday = _cursor.getString(_cursorIndexOfMHoliday);
            _item = new Holiday(_tmpMHoliday);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpMProduct;
            _tmpMProduct = _cursor.getString(_cursorIndexOfMProduct);
            _item.setProduct(_tmpMProduct);
            final String _tmpMNotes;
            _tmpMNotes = _cursor.getString(_cursorIndexOfMNotes);
            _item.setNotes(_tmpMNotes);
            final String _tmpMOpinion;
            _tmpMOpinion = _cursor.getString(_cursorIndexOfMOpinion);
            _item.setOpinion(_tmpMOpinion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<Holiday>> getWhopperHolidays() {
    final String _sql = "SELECT * from holiday_table where product = \"Whopper\" ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Holiday>>() {
      private Observer _observer;

      @Override
      protected List<Holiday> compute() {
        if (_observer == null) {
          _observer = new Observer("holiday_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfMHoliday = _cursor.getColumnIndexOrThrow("holiday");
          final int _cursorIndexOfMProduct = _cursor.getColumnIndexOrThrow("product");
          final int _cursorIndexOfMNotes = _cursor.getColumnIndexOrThrow("notes");
          final int _cursorIndexOfMOpinion = _cursor.getColumnIndexOrThrow("opinion");
          final List<Holiday> _result = new ArrayList<Holiday>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Holiday _item;
            final String _tmpMHoliday;
            _tmpMHoliday = _cursor.getString(_cursorIndexOfMHoliday);
            _item = new Holiday(_tmpMHoliday);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpMProduct;
            _tmpMProduct = _cursor.getString(_cursorIndexOfMProduct);
            _item.setProduct(_tmpMProduct);
            final String _tmpMNotes;
            _tmpMNotes = _cursor.getString(_cursorIndexOfMNotes);
            _item.setNotes(_tmpMNotes);
            final String _tmpMOpinion;
            _tmpMOpinion = _cursor.getString(_cursorIndexOfMOpinion);
            _item.setOpinion(_tmpMOpinion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public int getLatestHolidayId() {
    final String _sql = "SELECT id from holiday_table ORDER BY id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
