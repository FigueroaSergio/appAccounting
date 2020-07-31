package myaplicacion.com.database


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_expenses.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListExpenses : AppCompatActivity(),myListAdapter.onClickListener {


    lateinit var accountsDao: AccountsDao
    lateinit var myListAdapter: myListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_expenses)

        accountsDao = appDatabase.getInstance(this).accountsDao()

        val datos = ArrayList<Accounts>(accountsDao.getAccounts())
        myListAdapter = myListAdapter(datos,this)

        viewDatabse.adapter= myListAdapter
        viewDatabse.layoutManager=LinearLayoutManager(this)

       printTotal(datos)
    }
    fun printTotal(datos:ArrayList<Accounts>){
        var total:Int=0
        for (i in datos){
            val x = i.price
            total += x
        }
        viewtotal.setText(total.toString())
    }

    override fun onItemClick(dato:Accounts,position:Int) {
        val customdialog = CustomDialog.Builder()
            .setTitle("Precaucion")
            .setDescription("Esta seguro que desea eliminar")
            .setPositiveText("Ok")
            .setNegativeText("Cancel")
            .build()


        customdialog.show(supportFragmentManager,"custom dialog 1")


        customdialog.setDialogButtonListener(object : CustomDialog.DialogClickListener{
            override fun onPositiveBtn() {
                accountsDao.deleteAccount(dato)
                myListAdapter.eliminarItem(position)
                printTotal(myListAdapter.getElements())
                customdialog.dismiss()
            }

            override fun onNegativeBtn() {
                customdialog.dismiss()
            }

        })
    }


}