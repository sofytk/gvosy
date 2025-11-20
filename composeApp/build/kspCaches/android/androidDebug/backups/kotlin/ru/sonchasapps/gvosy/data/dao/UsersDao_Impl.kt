package ru.sonchasapps.gvosy.`data`.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.EntityUpsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
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
import ru.sonchasapps.gvosy.`data`.models.UserEntity

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class UsersDao_Impl(
  __db: RoomDatabase,
) : UsersDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUserEntity: EntityInsertAdapter<UserEntity>

  private val __deleteAdapterOfUserEntity: EntityDeleteOrUpdateAdapter<UserEntity>

  private val __updateAdapterOfUserEntity: EntityDeleteOrUpdateAdapter<UserEntity>

  private val __upsertAdapterOfUserEntity: EntityUpsertAdapter<UserEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfUserEntity = object : EntityInsertAdapter<UserEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `users_database` (`id`,`userName`,`userEmail`,`userToken`,`isPremium`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.userName)
        val _tmpUserEmail: String? = entity.userEmail
        if (_tmpUserEmail == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpUserEmail)
        }
        val _tmpUserToken: String? = entity.userToken
        if (_tmpUserToken == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpUserToken)
        }
        val _tmp: Int = if (entity.isPremium) 1 else 0
        statement.bindLong(5, _tmp.toLong())
      }
    }
    this.__deleteAdapterOfUserEntity = object : EntityDeleteOrUpdateAdapter<UserEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `users_database` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfUserEntity = object : EntityDeleteOrUpdateAdapter<UserEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `users_database` SET `id` = ?,`userName` = ?,`userEmail` = ?,`userToken` = ?,`isPremium` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.userName)
        val _tmpUserEmail: String? = entity.userEmail
        if (_tmpUserEmail == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpUserEmail)
        }
        val _tmpUserToken: String? = entity.userToken
        if (_tmpUserToken == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpUserToken)
        }
        val _tmp: Int = if (entity.isPremium) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.id)
      }
    }
    this.__upsertAdapterOfUserEntity = EntityUpsertAdapter<UserEntity>(object : EntityInsertAdapter<UserEntity>() {
      protected override fun createQuery(): String = "INSERT INTO `users_database` (`id`,`userName`,`userEmail`,`userToken`,`isPremium`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.userName)
        val _tmpUserEmail: String? = entity.userEmail
        if (_tmpUserEmail == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpUserEmail)
        }
        val _tmpUserToken: String? = entity.userToken
        if (_tmpUserToken == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpUserToken)
        }
        val _tmp: Int = if (entity.isPremium) 1 else 0
        statement.bindLong(5, _tmp.toLong())
      }
    }, object : EntityDeleteOrUpdateAdapter<UserEntity>() {
      protected override fun createQuery(): String = "UPDATE `users_database` SET `id` = ?,`userName` = ?,`userEmail` = ?,`userToken` = ?,`isPremium` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.userName)
        val _tmpUserEmail: String? = entity.userEmail
        if (_tmpUserEmail == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpUserEmail)
        }
        val _tmpUserToken: String? = entity.userToken
        if (_tmpUserToken == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpUserToken)
        }
        val _tmp: Int = if (entity.isPremium) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.id)
      }
    })
  }

  public override suspend fun insert(user: UserEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfUserEntity.insert(_connection, user)
  }

  public override suspend fun delete(user: UserEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfUserEntity.handle(_connection, user)
  }

  public override suspend fun update(user: UserEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfUserEntity.handle(_connection, user)
  }

  public override suspend fun upsert(user: UserEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __upsertAdapterOfUserEntity.upsert(_connection, user)
  }

  public override suspend fun getUser(): UserEntity? {
    val _sql: String = "SELECT * from users_database"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserName: Int = getColumnIndexOrThrow(_stmt, "userName")
        val _columnIndexOfUserEmail: Int = getColumnIndexOrThrow(_stmt, "userEmail")
        val _columnIndexOfUserToken: Int = getColumnIndexOrThrow(_stmt, "userToken")
        val _columnIndexOfIsPremium: Int = getColumnIndexOrThrow(_stmt, "isPremium")
        val _result: UserEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserName: String
          _tmpUserName = _stmt.getText(_columnIndexOfUserName)
          val _tmpUserEmail: String?
          if (_stmt.isNull(_columnIndexOfUserEmail)) {
            _tmpUserEmail = null
          } else {
            _tmpUserEmail = _stmt.getText(_columnIndexOfUserEmail)
          }
          val _tmpUserToken: String?
          if (_stmt.isNull(_columnIndexOfUserToken)) {
            _tmpUserToken = null
          } else {
            _tmpUserToken = _stmt.getText(_columnIndexOfUserToken)
          }
          val _tmpIsPremium: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsPremium).toInt()
          _tmpIsPremium = _tmp != 0
          _result = UserEntity(_tmpId,_tmpUserName,_tmpUserEmail,_tmpUserToken,_tmpIsPremium)
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
