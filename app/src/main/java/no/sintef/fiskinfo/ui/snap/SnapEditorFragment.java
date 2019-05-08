package no.sintef.fiskinfo.ui.snap;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.TextView;

import java.util.ArrayList;

import no.sintef.fiskinfo.R;
import no.sintef.fiskinfo.databinding.FragmentSnapEditorBinding;
import no.sintef.fiskinfo.model.Snap;

import static android.app.Activity.RESULT_OK;

public class SnapEditorFragment extends Fragment {

    private SnapViewModel mViewModel;
    private FragmentSnapEditorBinding mBinding;

    public static SnapEditorFragment newInstance() {
        return new SnapEditorFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_snap_editor, container, false);
        setHasOptionsMenu(true);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SnapViewModel.class);
        mViewModel.getDraft().observe(this, new Observer<Snap>() {
            @Override
            public void onChanged(Snap snap) {
                if (snap != null) {
                    mBinding.setSnap(snap);
                    mBinding.setEchogram(snap.echogram);
                    mBinding.setHandlers(SnapEditorFragment.this);
                }
            }
        });
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

    static final int PICK_CONTACT_REQUEST = 1;

    public void onSelectFromContactsClicked(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = resultIntent.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using <code><a href="/reference/android/content/CursorLoader.html">CursorLoader</a></code> to perform the query.
                Cursor cursor = getContext().getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                ArrayList<String> result = new ArrayList<>();
                while (!cursor.isAfterLast()) {
                    // Retrieve the phone number from the NUMBER column
                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(column);
                    result.add(number);

                    cursor.moveToNext();
                }
                mViewModel.getDraft().getValue().receivers = result;

                // Do something with the phone number...
            }
        }
    }
}
