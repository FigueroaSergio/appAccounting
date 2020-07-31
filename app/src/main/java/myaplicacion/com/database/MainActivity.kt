package myaplicacion.com.database


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var accountsDao: AccountsDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accountsDao = appDatabase.getInstance(this).accountsDao()

        btn_send.setOnClickListener {
            if (verification(plh_description) || verification(plh_value)) {
                Toast.makeText(this, "Por favor llene todas las casillas", Toast.LENGTH_LONG).show()
            } else {
                val description = plh_description.text.toString()

                val value: Int = plh_value.text.toString().toInt()
                val date = getdate()
                val newAccount = Accounts(description = description, date = date, price = value)
                accountsDao.insertAccount(newAccount)
                plh_description.text.clear()
                plh_value.text.clear()


                hidenkeyboard()
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
    fun View.hide(){
        if (this.hasFocus()){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.windowToken, 0)
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

}