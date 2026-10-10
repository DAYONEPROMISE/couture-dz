package dz.couture.book.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val phone: String = "",
    val notes: String = ""
)

@Entity(tableName = "measurements")
data class Measurement(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientId: Long,
    val chest: Float = 0f,
    val waist: Float = 0f,
    val hips: Float = 0f,
    val shoulder: Float = 0f,
    val sleeve: Float = 0f,
    val length: Float = 0f,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "orders")
data class TailorOrder(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientId: Long,
    val fabric: String = "",
    val model: String = "",
    val deadlineEpoch: Long = 0,
    val priceDzd: Long = 0,
    val depositDzd: Long = 0,
    val status: String = "NEW",
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface CoutureDao {
    @Insert suspend fun insertClient(c: Client): Long
    @Query("SELECT * FROM clients ORDER BY name") fun clients(): Flow<List<Client>>
    @Insert suspend fun insertMeasurement(m: Measurement): Long
    @Query("SELECT * FROM measurements WHERE clientId=:cid ORDER BY createdAt DESC") fun measurements(cid: Long): Flow<List<Measurement>>
    @Insert suspend fun insertOrder(o: TailorOrder): Long
    @Query("SELECT * FROM orders ORDER BY deadlineEpoch") fun orders(): Flow<List<TailorOrder>>
    @Query("UPDATE orders SET status=:s WHERE id=:id") suspend fun setStatus(id: Long, s: String)
}

@Database(entities = [Client::class, Measurement::class, TailorOrder::class], version = 1, exportSchema = false)
abstract class CoutureDb : RoomDatabase() {
    abstract fun dao(): CoutureDao
}
