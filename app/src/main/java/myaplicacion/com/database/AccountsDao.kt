package myaplicacion.com.database

import androidx.room.*

@Dao
interface AccountsDao {
    @Insert
    fun insertAccount(account:Accounts)

    @Update
    fun updateAccount(account: Accounts)

    @Delete
    fun deleteAccount(account: Accounts)

    @Query("SELECT * FROM accounts")
    fun getAccounts():List<Accounts>

}