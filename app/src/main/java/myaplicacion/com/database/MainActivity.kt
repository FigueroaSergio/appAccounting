package myaplicacion.com.database


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

import kotlinx.android.synthetic.main.activity_main.*
import java.security.Key
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    lateinit var accountsDao: AccountsDao
     val key="Cuentas"
     val ahorro="Ahorro"
     val gastos="Gastos"

    lateinit var sharedPreferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1500)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(key,Context.MODE_PRIVATE)


        accountsDao = appDatabase.getInstance(this).accountsDao()

        btn_gasto.setOnClickListener {
            if (verification(plh_description) || verification(plh_value)) {
                showMessage("Por favor llene todas las casillas")
            } else {
                getData(false)


            }
        }
        btn_ganancia.setOnClickListener {
            if (verification(plh_description) || verification(plh_value)) {
                showMessage("Por favor llene todas las casillas")
            } else {
                getData(true)
            }
        }

        mainView.setOnClickListener { view ->
            // Aca tambien se puede poner la funcion hidekeyboard
            if (view.hasFocus()) {

                Toast.makeText(this, "tocando patalla", Toast.LENGTH_SHORT).show()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }





        myAccount.setOnClickListener {
            val intent = Intent(this, ListExpenses::class.java)
            startActivity(intent)
        }


    }


    fun hidenkeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun getData(boolean: Boolean) {

        val editor = sharedPreferences.edit()
        var miAhorro = sharedPreferences.getLong(ahorro, 0L)
        var misGastos = sharedPreferences.getLong(gastos, 0L)
        val description = plh_description.text.toString()
        val value: Int = plh_value.text.toString().toInt()
        val date = getdate()


        val newAccount = Accounts(
            description = description,
            date = date,
            price = value,
            moreOrless = boolean
        )

        accountsDao.insertAccount(newAccount)
        plh_description.text.clear()
        plh_value.text.clear()
        hidenkeyboard()

        if (boolean) {
            miAhorro+=value
            editor.putLong(ahorro, miAhorro)
            editor.apply()

        } else {
            miAhorro-=value
            misGastos+=value
            editor.putLong(ahorro, miAhorro)
            editor.putLong(gastos, misGastos)
            editor.apply()

        }
    }
}