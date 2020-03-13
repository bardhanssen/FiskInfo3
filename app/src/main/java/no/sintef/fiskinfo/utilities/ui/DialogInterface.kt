/**
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

interface DialogInterface {
    /**
     * Return a dialog with the given layout and title. The dialog defaults to not being cancelled by outside touch.
     * @param context
     * @param layoutId
     * @param titleId
     * @return
     */
    fun getDialog(
        context: Context?,
        layoutId: Int,
        titleId: Int
    ): Dialog?

    /**
     * Return a dialog with the given layout and title. The dialog defaults to not being cancelled by outside touch.
     * @param context
     * @param layoutId
     * @param title
     * @return
     */
    fun getDialog(
        context: Context?,
        layoutId: Int,
        title: String?
    ): Dialog?

    /**
     * Return a dialog with the given layout, title, and icon. The dialog defaults to not being cancelled by outside touch.
     * @param context
     * @param layoutId
     * @param title
     * @param iconId
     * @return

    fun getDialogWithTitleIcon(
        context: Context?,
        layoutId: Int,
        title: String?,
        iconId: Int
    ): Dialog?

    /**
     * Return an alert dialog with the given title and info text. The dialog defaults to not being cancelled by outside touch.
     * @param context
     * @param title
     * @param message
     * @return
     */
    fun getHyperlinkAlertDialog(
        context: Context?,
        title: String?,
        message: String?
    ): AlertDialog?

    fun getCheckboxInformationDialog(
        context: Context?,
        title: String?,
        message: String?
    ): Dialog?

    */
}