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
package no.sintef.fiskinfo.ui.snap

import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FilterQueryProvider
import android.widget.MultiAutoCompleteTextView

import no.sintef.fiskinfo.R
import no.sintef.fiskinfo.databinding.SnapEditorFragmentBinding
import no.sintef.fiskinfo.model.SnapMessage

class SnapEditorFragment : Fragment() {

    private lateinit var mViewModel: SnapViewModel
    private var _mBinding: SnapEditorFragmentBinding? = null
    private val mBinding get() = _mBinding!! // Only valid between onCreateView and onDestroyView.

    internal var mContentResolver: ContentResolver? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mBinding = DataBindingUtil.inflate<SnapEditorFragmentBinding>(
            inflater,
            R.layout.snap_editor_fragment,
            container,
            false
        )
        setHasOptionsMenu(true)

        mContentResolver = requireContext().contentResolver
        mBinding.snapReceiverEditText.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        mBinding.snapReceiverEditText.setThreshold(1)

        val from = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.Contacts.Photo.PHOTO_URI
        )

        val to = intArrayOf(R.id.tv_contact_name, R.id.tv_contact_email, R.id.iv_contact_photo)

        val adapter = object : SimpleCursorAdapter(requireContext(), R.layout.contact_row, null, from, to, 0) {
            override fun convertToString(cursor: Cursor): CharSequence {

                val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                return cursor.getString(emailIndex)
            }
        }

        adapter.filterQueryProvider = FilterQueryProvider { constraint ->
            if (constraint == null) {
                return@FilterQueryProvider null
            }

            val query = constraint.toString()

            val selection = (ContactsContract.Contacts.DISPLAY_NAME
                    + " LIKE ? "
                    + " OR "
                    + ContactsContract.CommonDataKinds.Email.ADDRESS
                    + " LIKE ? ")

            val selectionArgs = arrayOf("%$query%", "%$query%")

            mContentResolver?.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null
            )
        }

        mBinding.snapReceiverEditText.setAdapter(adapter)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(requireActivity()).get(SnapViewModel::class.java)
        mViewModel.draft.observe(viewLifecycleOwner, Observer { snap ->
            if (snap != null) {
                mBinding.snap = snap
                //mBinding!!.setEchogram(mViewModel?.draftMetadata)
                mBinding.handlers = this@SnapEditorFragment
                mBinding.snapviewmodel = mViewModel
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requireActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.snap_editor_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.send_snap_action) {
            mViewModel.sendSnapAndClear()
            Navigation.findNavController(requireView()).navigateUp()
            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }


    companion object {

        fun newInstance(): SnapEditorFragment {
            return SnapEditorFragment()
        }

        private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }
}
