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
import ru.sonchasapps.gvosy.`data`.dao.AssistantDao
import ru.sonchasapps.gvosy.`data`.dao.AssistantDao_Impl

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AssistantDatabase_Impl : AssistantDatabase() {
  private val _assistantDao: Lazy<AssistantDao> = lazy {
    AssistantDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "5c8fb574acba09f47c2eeb88a43f53dd", "7b030d842e52604ac615c434746e4bea") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `assistant_data` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `assistantName` TEXT NOT NULL, `assistantAge` INTEGER NOT NULL, `assistantSex` INTEGER NOT NULL, `assistantDescription` TEXT, `assistantImg` TEXT, `assistantMessageLimit` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5c8fb574acba09f47c2eeb88a43f53dd')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `assistant_data`")
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
        val _columnsAssistantData: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAssistantData.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAssistantData.put("assistantName", TableInfo.Column("assistantName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAssistantData.put("assistantAge", TableInfo.Column("assistantAge", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAssistantData.put("assistantSex", TableInfo.Column("assistantSex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAssistantData.put("assistantDescription", TableInfo.Column("assistantDescription", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAssistantData.put("assistantImg", TableInfo.Column("assistantImg", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAssistantData.put("assistantMessageLimit", TableInfo.Column("assistantMessageLimit", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAssistantData: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesAssistantData: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoAssistantData: TableInfo = TableInfo("assistant_data", _columnsAssistantData, _foreignKeysAssistantData, _indicesAssistantData)
        val _existingAssistantData: TableInfo = read(connection, "assistant_data")
        if (!_infoAssistantData.equals(_existingAssistantData)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |assistant_data(ru.sonchasapps.gvosy.data.models.AssistantEntity).
              | Expected:
              |""".trimMargin() + _infoAssistantData + """
              |
              | Found:
              |""".trimMargin() + _existingAssistantData)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "assistant_data")
  }

  public override fun clearAllTables() {
    super.performClear(false, "assistant_data")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(AssistantDao::class, AssistantDao_Impl.getRequiredConverters())
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

  public override fun getAssistantDao(): AssistantDao = _assistantDao.value
}
