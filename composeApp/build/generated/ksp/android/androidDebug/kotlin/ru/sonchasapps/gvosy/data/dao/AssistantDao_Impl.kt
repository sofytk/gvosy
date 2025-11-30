package ru.sonchasapps.gvosy.`data`.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performBlocking
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass
import ru.sonchasapps.gvosy.`data`.models.AssistantEntity

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AssistantDao_Impl(
  __db: RoomDatabase,
) : AssistantDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAssistantEntity: EntityInsertAdapter<AssistantEntity>

  private val __deleteAdapterOfAssistantEntity: EntityDeleteOrUpdateAdapter<AssistantEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAssistantEntity = object : EntityInsertAdapter<AssistantEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `assistant_data` (`id`,`assistantName`,`assistantAge`,`assistantSex`,`assistantDescription`,`assistantImg`,`assistantMessageLimit`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AssistantEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.assistantName)
        statement.bindLong(3, entity.assistantAge.toLong())
        val _tmp: Int = if (entity.assistantSex) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        val _tmpAssistantDescription: String? = entity.assistantDescription
        if (_tmpAssistantDescription == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpAssistantDescription)
        }
        val _tmpAssistantImg: String? = entity.assistantImg
        if (_tmpAssistantImg == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpAssistantImg)
        }
        statement.bindLong(7, entity.assistantMessageLimit.toLong())
      }
    }
    this.__deleteAdapterOfAssistantEntity = object : EntityDeleteOrUpdateAdapter<AssistantEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `assistant_data` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AssistantEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun add(assistantEntity: AssistantEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfAssistantEntity.insert(_connection, assistantEntity)
  }

  public override suspend fun delete(assistantEntity: AssistantEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfAssistantEntity.handle(_connection, assistantEntity)
  }

  public override fun getAssistant(): AssistantEntity? {
    val _sql: String = "SELECT * from assistant_data"
    return performBlocking(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAssistantName: Int = getColumnIndexOrThrow(_stmt, "assistantName")
        val _columnIndexOfAssistantAge: Int = getColumnIndexOrThrow(_stmt, "assistantAge")
        val _columnIndexOfAssistantSex: Int = getColumnIndexOrThrow(_stmt, "assistantSex")
        val _columnIndexOfAssistantDescription: Int = getColumnIndexOrThrow(_stmt, "assistantDescription")
        val _columnIndexOfAssistantImg: Int = getColumnIndexOrThrow(_stmt, "assistantImg")
        val _columnIndexOfAssistantMessageLimit: Int = getColumnIndexOrThrow(_stmt, "assistantMessageLimit")
        val _result: AssistantEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAssistantName: String
          _tmpAssistantName = _stmt.getText(_columnIndexOfAssistantName)
          val _tmpAssistantAge: Int
          _tmpAssistantAge = _stmt.getLong(_columnIndexOfAssistantAge).toInt()
          val _tmpAssistantSex: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfAssistantSex).toInt()
          _tmpAssistantSex = _tmp != 0
          val _tmpAssistantDescription: String?
          if (_stmt.isNull(_columnIndexOfAssistantDescription)) {
            _tmpAssistantDescription = null
          } else {
            _tmpAssistantDescription = _stmt.getText(_columnIndexOfAssistantDescription)
          }
          val _tmpAssistantImg: String?
          if (_stmt.isNull(_columnIndexOfAssistantImg)) {
            _tmpAssistantImg = null
          } else {
            _tmpAssistantImg = _stmt.getText(_columnIndexOfAssistantImg)
          }
          val _tmpAssistantMessageLimit: Int
          _tmpAssistantMessageLimit = _stmt.getLong(_columnIndexOfAssistantMessageLimit).toInt()
          _result = AssistantEntity(_tmpId,_tmpAssistantName,_tmpAssistantAge,_tmpAssistantSex,_tmpAssistantDescription,_tmpAssistantImg,_tmpAssistantMessageLimit)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
