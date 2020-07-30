package myaplicacion.com.database

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.costum_dialog.*
import myaplicacion.com.database.R.style.Theme_Dialog_Transparent as Theme_Dialog_Transparent

class CustomDialog private constructor(
    private val positiveButton: String?,
    private val negativeButton: String?,
    private val titleText: String?,
    private val description: String?,
    private var dialogClickListener: DialogClickListener? = null
) : DialogFragment() {
    interface DialogClickListener {
        fun onPositiveBtn()
        fun onNegativeBtn()
    }

    data class Builder(
        private var positiveButton: String? = null,
        private var negativeButton: String? = null,
        private var titleText: String? = null,
        private var description: String? = null

    ) {

        fun setPositiveText(positiveButton: String) = apply { this.positiveButton = positiveButton }
        fun setNegativeText(negativeButton: String) = apply { this.negativeButton = negativeButton }
        fun setTitle(titleText: String) = apply { this.titleText = titleText }
        fun setDescription(description: String) = apply { this.description = description }

        fun build() = CustomDialog(positiveButton, negativeButton, titleText, description)


    }

    fun setDialogButtonListener(listener: DialogClickListener) =
        apply { dialogClickListener = listener }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.costum_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialogTitle.text = titleText
        dialogDes.text = description

        dialogCancel.text = negativeButton
        dialogCancel.setOnClickListener { dialogClickListener!!.onNegativeBtn() }
        dialogOk.text = positiveButton
        dialogOk.setOnClickListener { dialogClickListener!!.onPositiveBtn() }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
         val dialog=super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }



}
