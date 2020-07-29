package myaplicacion.com.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Accounts(@PrimaryKey(autoGenerate = true)
                    val id:Int=0,
                    val description:String,
                    val date:String,
                    val price:Int)