package ru.sonchasapps.gvosy.`data`.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performBlocking
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
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

  private val __updateAdapterOfAssistantEntity: EntityDeleteOrUpdateAdapter<AssistantEntity>

  private val __upsertAdapterOfAssistantEntity: EntityUpsertAdapter<AssistantEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAssistantEntity = object : EntityInsertAdapter<AssistantEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `assistant_data` (`id`,`assistantName`,`assistantAge`,`assistantSex`,`assistantDescription`,`userId`,`assistantImg`,`assistantMessageLimit`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AssistantEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.assistantName)
        statement.bindLong(3, entity.assistantAge.toLong())
        statement.bindText(4, entity.assistantSex)
        val _tmpAssistantDescription: String? = entity.assistantDescription
        if (_tmpAssistantDescription == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpAssistantDescription)
        }
        statement.bindLong(6, entity.userId)
        val _tmpAssistantImg: String? = entity.assistantImg
        if (_tmpAssistantImg == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpAssistantImg)
        }
        statement.bindLong(8, entity.assistantMessageLimit.toLong())
      }
    }
    this.__deleteAdapterOfAssistantEntity = object : EntityDeleteOrUpdateAdapter<AssistantEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `assistant_data` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AssistantEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfAssistantEntity = object : EntityDeleteOrUpdateAdapter<AssistantEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `assistant_data` SET `id` = ?,`assistantName` = ?,`assistantAge` = ?,`assistantSex` = ?,`assistantDescription` = ?,`userId` = ?,`assistantImg` = ?,`assistantMessageLimit` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AssistantEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.assistantName)
        statement.bindLong(3, entity.assistantAge.toLong())
        statement.bindText(4, entity.assistantSex)
        val _tmpAssistantDescription: String? = entity.assistantDescription
        if (_tmpAssistantDescription == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpAssistantDescription)
        }
        statement.bindLong(6, entity.userId)
        val _tmpAssistantImg: String? = entity.assistantImg
        if (_tmpAssistantImg == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpAssistantImg)
        }
        statement.bindLong(8, entity.assistantMessageLimit.toLong())
        statement.bindLong(9, entity.id)
      }
    }
    this.__upsertAdapterOfAssistantEntity = EntityUpsertAdapter<AssistantEntity>(object : EntityInsertAdapter<AssistantEntity>() {
      protected override fun createQuery(): String = "INSERT INTO `assistant_data` (`id`,`assistantName`,`assistantAge`,`assistantSex`,`assistantDescription`,`userId`,`assistantImg`,`assistantMessageLimit`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AssistantEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.assistantName)
        statement.bindLong(3, entity.assistantAge.toLong())
        statement.bindText(4, entity.assistantSex)
        val _tmpAssistantDescription: String? = entity.assistantDescription
        if (_tmpAssistantDescription == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpAssistantDescription)
        }
        statement.bindLong(6, entity.userId)
        val _tmpAssistantImg: String? = entity.assistantImg
        if (_tmpAssistantImg == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpAssistantImg)
        }
        statement.bindLong(8, entity.assistantMessageLimit.toLong())
      }
    }, object : EntityDeleteOrUpdateAdapter<AssistantEntity>() {
      protected override fun createQuery(): String = "UPDATE `assistant_data` SET `id` = ?,`assistantName` = ?,`assistantAge` = ?,`assistantSex` = ?,`assistantDescription` = ?,`userId` = ?,`assistantImg` = ?,`assistantMessageLimit` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AssistantEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.assistantName)
        statement.bindLong(3, entity.assistantAge.toLong())
        statement.bindText(4, entity.assistantSex)
        val _tmpAssistantDescription: String? = entity.assistantDescription
        if (_tmpAssistantDescription == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpAssistantDescription)
        }
        statement.bindLong(6, entity.userId)
        val _tmpAssistantImg: String? = entity.assistantImg
        if (_tmpAssistantImg == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpAssistantImg)
        }
        statement.bindLong(8, entity.assistantMessageLimit.toLong())
        statement.bindLong(9, entity.id)
      }
    })
  }

  public override suspend fun insert(assistantEntity: AssistantEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfAssistantEntity.insert(_connection, assistantEntity)
  }

  public override suspend fun delete(assistantEntity: AssistantEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfAssistantEntity.handle(_connection, assistantEntity)
  }

  public override suspend fun update(assistantEntity: AssistantEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfAssistantEntity.handle(_connection, assistantEntity)
  }

  public override suspend fun upsert(assistantEntity: AssistantEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __upsertAdapterOfAssistantEntity.upsert(_connection, assistantEntity)
  }

  public override fun getAssistant(id: Long): AssistantEntity? {
    val _sql: String = "SELECT * from assistant_data WHERE id = ?"
    return performBlocking(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAssistantName: Int = getColumnIndexOrThrow(_stmt, "assistantName")
        val _columnIndexOfAssistantAge: Int = getColumnIndexOrThrow(_stmt, "assistantAge")
        val _columnIndexOfAssistantSex: Int = getColumnIndexOrThrow(_stmt, "assistantSex")
        val _columnIndexOfAssistantDescription: Int = getColumnIndexOrThrow(_stmt, "assistantDescription")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
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
          val _tmpAssistantSex: String
          _tmpAssistantSex = _stmt.getText(_columnIndexOfAssistantSex)
          val _tmpAssistantDescription: String?
          if (_stmt.isNull(_columnIndexOfAssistantDescription)) {
            _tmpAssistantDescription = null
          } else {
            _tmpAssistantDescription = _stmt.getText(_columnIndexOfAssistantDescription)
          }
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpAssistantImg: String?
          if (_stmt.isNull(_columnIndexOfAssistantImg)) {
            _tmpAssistantImg = null
          } else {
            _tmpAssistantImg = _stmt.getText(_columnIndexOfAssistantImg)
          }
          val _tmpAssistantMessageLimit: Int
          _tmpAssistantMessageLimit = _stmt.getLong(_columnIndexOfAssistantMessageLimit).toInt()
          _result = AssistantEntity(_tmpId,_tmpAssistantName,_tmpAssistantAge,_tmpAssistantSex,_tmpAssistantDescription,_tmpUserId,_tmpAssistantImg,_tmpAssistantMessageLimit)
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
