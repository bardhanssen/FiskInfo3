package no.sintef.fiskinfo.ui.snap;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.MultiAutoCompleteTextView;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.databinding.SnapEditorFragmentBinding;
import no.sintef.fiskinfo.model.SnapMessage;

public class SnapEditorFragment extends Fragment {

    private SnapViewModel mViewModel;
    private SnapEditorFragmentBinding mBinding;

    public static SnapEditorFragment newInstance() {
        return new SnapEditorFragment();
    }

    ContentResolver mContentResolver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.snap_editor_fragment, container, false);
        setHasOptionsMenu(true);

        mContentResolver = getContext().getContentResolver();
        mBinding.snapReceiverEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mBinding.snapReceiverEditText.setThreshold(1);

        final String[] from = new String[]{ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.Contacts.Photo.PHOTO_URI};

        final int[] to = new int[]{R.id.tv_contact_name,
                R.id.tv_contact_email,
                R.id.iv_contact_photo};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.contact_row, null, from, to, 0) {
            @Override
            public CharSequence convertToString(Cursor cursor) {

                final int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
                return cursor.getString(emailIndex);
            }
        };

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                if (constraint == null) {
                    return null;
                }

                String query = constraint.toString();

                final String selection = ContactsContract.Contacts.DISPLAY_NAME
                        + " LIKE ? "
                        + " OR "
                        + ContactsContract.CommonDataKinds.Email.ADDRESS
                        + " LIKE ? ";

                String[] selectionArgs = new String[]{"%" + query + "%"
                        , "%" + query + "%"};

                return mContentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, selection, selectionArgs, null);

            }
        });

        mBinding.snapReceiverEditText.setAdapter(adapter);

        return mBinding.getRoot();
    }
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getDraft().observe(this, new Observer<SnapMessage>() {
            @Override
            public void onChanged(SnapMessage snap) {
                if (snap != null) {
                    mBinding.setSnap(snap);
                    mBinding.setEchogram(snap.echogramInfo);
                    mBinding.setHandlers(SnapEditorFragment.this);
                    mBinding.setSnapviewmodel(mViewModel);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.snap_editor_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.send_snap_action) {
            mViewModel.sendSnapAndClear();
            Navigation.findNavController(this.getView()).navigateUp();
            return true;
        }
        return false;
    }
}
