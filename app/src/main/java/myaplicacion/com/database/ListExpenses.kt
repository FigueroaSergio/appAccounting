package myaplicacion.com.database


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_expenses.*

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
        var total =0
        for (i in datos){
            val x = i.price
            total += x
        }
        viewtotal.setText(total.toString())
    }

    override fun onItemClick(dato:Accounts,position:Int) {
        val customdialog = CustomDialog.Builder()
            .setTitle("Caution")
            .setDescription("Are you sure you want to delete the item ${dato.description} $${dato.price}")
            .setPositiveText("Ok")
            .setNegativeText("Cancel")
            .build()


        customdialog.show(supportFragmentManager,"custom dialog 1")


        customdialog.setDialogButtonListener(object : CustomDialog.DialogClickListener{
            override fun onPositiveBtn() {
                accountsDao.deleteAccount(dato)
                myListAdapter.eliminarItem(position)
                myListAdapter.rechargerNumber()
                printTotal(myListAdapter.getElements())
                customdialog.dismiss()
            }

            override fun onNegativeBtn() {
                customdialog.dismiss()
            }

        })
    }


}