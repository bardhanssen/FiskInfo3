/**
 * Copyright (C) 2020 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo.utilities.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
//import fiskinfoo.no.sintef.fiskinfoo.Baseclasses.HyperlinkAlertDialog
//import fiskinfoo.no.sintef.fiskinfoo.R

class UtilityDialogs : DialogInterface {
    override fun getDialog(
        context: Context?,
        layoutId: Int,
        titleId: Int
    ): Dialog? {
        if (context == null)
            return null
        val dialog = Dialog(context!!)
        dialog.setContentView(layoutId)
        dialog.setTitle(titleId)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return dialog
    }

    override fun getDialog(
        context: Context?,
        layoutId: Int,
        title: String?
    ): Dialog? {
        if (context == null)
            return null
        val dialog = Dialog(context!!)
        dialog.setContentView(layoutId)
        dialog.setTitle(title)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return dialog
    }
/*
    override fun getDialogWithTitleIcon(
        context: Context?,
        layoutId: Int,
        title: String?,
        iconId: Int
    ): Dialog? {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON)
        dialog.setContentView(layoutId)
        dialog.setTitle(title)
        dialog.setFeatureDrawableResource(
            Window.FEATURE_LEFT_ICON,
            R.drawable.ikon_kart_til_din_kartplotter
        )
        dialog.setCanceledOnTouchOutside(false)
        if (dialog.window != null) {
            dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
        return dialog
    }

    override fun getHyperlinkAlertDialog(
        context: Context?,
        title: String?,
        message: String?
    ): AlertDialog? {
        return HyperlinkAlertDialog.create(context, title, message)
    }

    override fun getCheckboxInformationDialog(
        context: Context?,
        title: String?,
        infoText: String?
    ): Dialog? {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_information_checkbox)
        dialog.setTitle(title)
        dialog.setCanceledOnTouchOutside(false)
        val textView =
            dialog.findViewById<View>(R.id.checkbox_dialog_text_view) as TextView
        textView.text = infoText
        if (dialog.window != null) {
            dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
        return dialog
    }*/
}