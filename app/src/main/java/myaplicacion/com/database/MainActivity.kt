package myaplicacion.com.database

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.costum_dialog.*
import kotlinx.android.synthetic.main.costum_dialog.view.*
import java.nio.file.attribute.AclEntry


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
            }
        }
        myAccount.setOnClickListener {

            val intent = Intent(this, ListExpenses::class.java)
            startActivity(intent)


val customdialog = CustomDialog.Builder()
                .setTitle("Precaucion")
                .setDescription("Esta seguro que desea eliminar")
                .setPositiveText("Ok")
                .setNegativeText("Cancel")
                .build()


            customdialog.show(supportFragmentManager,"custom dialog 1")


            customdialog.setDialogButtonListener(object : CustomDialog.DialogClickListener{
                override fun onPositiveBtn() {
                    Toast.makeText(this@MainActivity, "hello",
                    Toast.LENGTH_SHORT).show()
                }

                override fun onNegativeBtn() {
                    customdialog.dismiss()
                }

            })

        }

    }


}