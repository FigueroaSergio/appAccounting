package myaplicacion.com.database


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_expenses.*

class ListExpenses : AppCompatActivity(),myListAdapter.onClickListener {


    lateinit var accountsDao: AccountsDao
    lateinit var myListAdapter: myListAdapter

    val key="Cuentas"
    val ahorro="Ahorro"
    val gastos="Gastos"
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_expenses)

        sharedPreferences = getSharedPreferences(key,Context.MODE_PRIVATE)

        accountsDao = appDatabase.getInstance(this).accountsDao()

        val datos = ArrayList<Accounts>(accountsDao.getAccounts())
        myListAdapter = myListAdapter(datos,this,this)

        viewDatabse.adapter= myListAdapter
        viewDatabse.layoutManager=LinearLayoutManager(this)
        var contador=false
        printTotal(false)
        detail.setOnClickListener{
            contador = !contador
            printTotal(contador)
        }

    }
    fun printTotal(bool: Boolean){
        val datos:Long
        if(bool){
            datos =sharedPreferences.getLong(ahorro,0)
        textTotal.setTextColor(Color.parseColor("#03DA64"))
        textTotal.setText("Ahorros: ")}
        else
           {
               datos = sharedPreferences.getLong(gastos, 0)
               textTotal.setTextColor(Color.parseColor("#FF4040"))
               textTotal.text = "Gastos: "
           }
        viewtotal.setText(datos.toString())


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
                val editor = sharedPreferences.edit()
                var miAhorro = sharedPreferences.getLong(ahorro, 0L)
                var misGastos = sharedPreferences.getLong(gastos, 0L)
                val price= dato.price
                val bool = dato.moreOrless
                accountsDao.deleteAccount(dato)
                myListAdapter.eliminarItem(position)
                myListAdapter.rechargerNumber()
                if (bool){
                    miAhorro-=price
                    editor.putLong(ahorro,miAhorro)
                    editor.apply()
                }
                else{
                    misGastos+=price
                    miAhorro+=price
                    editor.putLong(gastos,misGastos)
                    editor.putLong(ahorro,miAhorro)
                    editor.apply()
                }
                printTotal(bool)
                customdialog.dismiss()
            }

            override fun onNegativeBtn() {
                customdialog.dismiss()
            }

        })
    }


}