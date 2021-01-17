package com.example.android.SemanticApp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class HolidayRoomDatabase_Impl extends HolidayRoomDatabase {
  private volatile HolidayDao _holidayDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(8) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `holiday_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `holiday` TEXT NOT NULL, `product` TEXT NOT NULL, `notes` TEXT, `opinion` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"0eaeac1f484691a7f972abd79d965183\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `holiday_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsHolidayTable = new HashMap<String, TableInfo.Column>(5);
        _columnsHolidayTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsHolidayTable.put("holiday", new TableInfo.Column("holiday", "TEXT", true, 0));
        _columnsHolidayTable.put("product", new TableInfo.Column("product", "TEXT", true, 0));
        _columnsHolidayTable.put("notes", new TableInfo.Column("notes", "TEXT", false, 0));
        _columnsHolidayTable.put("opinion", new TableInfo.Column("opinion", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHolidayTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHolidayTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHolidayTable = new TableInfo("holiday_table", _columnsHolidayTable, _foreignKeysHolidayTable, _indicesHolidayTable);
        final TableInfo _existingHolidayTable = TableInfo.read(_db, "holiday_table");
        if (! _infoHolidayTable.equals(_existingHolidayTable)) {
          throw new IllegalStateException("Migration didn't properly handle holiday_table(com.example.android.SemanticApp.Holiday).\n"
                  + " Expected:\n" + _infoHolidayTable + "\n"
                  + " Found:\n" + _existingHolidayTable);
        }
      }
    }, "0eaeac1f484691a7f972abd79d965183", "86de21ae876d0383e2839016bfc3ad8c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "holiday_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `holiday_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public HolidayDao holidayDao() {
    if (_holidayDao != null) {
      return _holidayDao;
    } else {
      synchronized(this) {
        if(_holidayDao == null) {
          _holidayDao = new HolidayDao_Impl(this);
        }
        return _holidayDao;
      }
    }
  }
}
