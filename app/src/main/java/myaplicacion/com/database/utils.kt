package myaplicacion.com.database

import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

val myformat= SimpleDateFormat("dd/M/yyyy")

fun getdate(): String {

    val date =  myformat.format(Date())
    return date
}


fun verification(view: EditText):Boolean {
    return view.text.toString().isEmpty()
}

