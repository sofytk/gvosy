package ru.sonchasapps.gvosy.`data`.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass
import ru.sonchasapps.gvosy.`data`.dao.UsersDao
import ru.sonchasapps.gvosy.`data`.dao.UsersDao_Impl

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class UsersDatabase_Impl : UsersDatabase() {
  private val _usersDao: Lazy<UsersDao> = lazy {
    UsersDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2, "a93dfb986787ecf7f0d4c4e2f75d9e3a", "4972a1955643ed850cd4a2966f4bbe9d") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `users_database` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userName` TEXT NOT NULL, `userEmail` TEXT, `userToken` TEXT, `isPremium` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a93dfb986787ecf7f0d4c4e2f75d9e3a')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `users_database`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsUsersDatabase: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUsersDatabase.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsersDatabase.put("userName", TableInfo.Column("userName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsersDatabase.put("userEmail", TableInfo.Column("userEmail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsersDatabase.put("userToken", TableInfo.Column("userToken", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUsersDatabase.put("isPremium", TableInfo.Column("isPremium", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUsersDatabase: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUsersDatabase: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUsersDatabase: TableInfo = TableInfo("users_database", _columnsUsersDatabase, _foreignKeysUsersDatabase, _indicesUsersDatabase)
        val _existingUsersDatabase: TableInfo = read(connection, "users_database")
        if (!_infoUsersDatabase.equals(_existingUsersDatabase)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |users_database(ru.sonchasapps.gvosy.data.models.UserEntity).
              | Expected:
              |""".trimMargin() + _infoUsersDatabase + """
              |
              | Found:
              |""".trimMargin() + _existingUsersDatabase)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "users_database")
  }

  public override fun clearAllTables() {
    super.performClear(false, "users_database")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(UsersDao::class, UsersDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun getUserDao(): UsersDao = _usersDao.value
}
