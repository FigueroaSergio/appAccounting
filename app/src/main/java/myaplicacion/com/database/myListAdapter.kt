package myaplicacion.com.database

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class myListAdapter(private val datos: ArrayList<Accounts>,
                    private val Listener:onClickListener,val context: Context) :
    RecyclerView.Adapter<myListAdapter.myHolder>() {
    interface onClickListener{
        fun onItemClick(dato:Accounts,position: Int)
    }
    fun eliminarItem(position: Int){
        datos.removeAt(position)
        notifyItemRemoved(position)
    }
    fun getElements(): ArrayList<Accounts> {
        return datos
    }
    fun rechargerNumber(){
        notifyDataSetChanged()
    }

    class myHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val id: TextView = itemview.findViewById(R.id.idDb)
        val description: TextView = itemview.findViewById(R.id.descriptionDb)
        val date: TextView = itemview.findViewById(R.id.dateDb)
        val value: TextView = itemview.findViewById(R.id.priceDb)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myListAdapter.myHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_database, parent, false)
        return myHolder(itemView)
    }

    override fun getItemCount(): Int = datos.size


    override fun onBindViewHolder(holder: myHolder, position: Int) {

        val dato = datos[position]
        holder.id.text = position.toString()
        holder.description.text = dato.description
        holder.date.text = dato.date
        holder.value.text = dato.price.toString()
        if (dato.moreOrless){
            holder.id.setTextColor(Color.parseColor("#03DA64"))}
        else{
            holder.id.setTextColor(Color.parseColor("#FF4040"))
        }
        holder.itemView.setOnClickListener{
            Listener.onItemClick(dato,holder.adapterPosition)
        }
    }
}