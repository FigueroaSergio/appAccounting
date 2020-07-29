package myaplicacion.com.database


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_expenses.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListExpenses : AppCompatActivity() {


    lateinit var accountsDao: AccountsDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_expenses)

        accountsDao = appDatabase.getInstance(this).accountsDao()

        val datos = ArrayList<Accounts>(accountsDao.getAccounts())

        val adapter = myListAdapter(datos)
        viewDatabse.adapter= adapter
        viewDatabse.layoutManager=LinearLayoutManager(this)
        var total:Int=0
        for (i in datos){
            val x = i.price
            total += x
        }
        viewtotal.setText(total.toString())
    }


}