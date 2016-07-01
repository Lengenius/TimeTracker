package com.android.frankthirteen.timetracker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.frankthirteen.timetracker.R;
import com.android.frankthirteen.timetracker.entities.Tracker;
import com.android.frankthirteen.timetracker.entities.TrackerLab;
import com.android.frankthirteen.timetracker.utils.FormatUtils;
import com.android.frankthirteen.timetracker.utils.LogUtils;
import com.android.frankthirteen.timetracker.utils.PictureUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Frank on 6/28/16.
 */
public class TrackerDefineFragment extends Fragment {

    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_PHOTO = 1;

    private static final String TAG = "TrackerDefineFragment";

    private UUID mId;
    private EditText trTitle, trContent, trComment, trTimeCost;
    private TextView trEndDate;
    private ImageButton iBtnTakePhoto;
    private Button btnChooseDate;
    private Tracker tracker;
    private ImageView trPhoto;

    private String currentPhotoPath;

    public static TrackerDefineFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(Tracker.EXTRA_ID, id);
        TrackerDefineFragment fragment = new TrackerDefineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = ((UUID) getArguments().getSerializable(Tracker.EXTRA_ID));
        TrackerLab trackerLab = TrackerLab.getTrackerLab(getActivity());
        if (TrackerLab.getTrackerLab(getActivity()).getTracker(mId) == null) {
            trackerLab.addTracker(new Tracker(mId));
        }
        tracker = TrackerLab.getTrackerLab(getActivity()).getTracker(mId);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //without the attachToRoot value, there will be an IllegalStateException: The specified child
        //already has a parent. You must call removeView() on the child's parent first.
        View rootView = inflater.inflate(R.layout.fragment_tracker_define, container, false);
        initialView(rootView);

        trTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tracker.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        trContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tracker.setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        trComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tracker.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        trTimeCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(s.toString()).equals("")) {
                    tracker.setPlanningTime(Integer.parseInt(s.toString()));
                }
            }
        });

        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d(TAG, "end date button clicked.");
                //set date picker fragment include target fragment,
                DatePickerDialogFragment datePicker = DatePickerDialogFragment.newInstance(new Date());
                datePicker.setTargetFragment(TrackerDefineFragment.this, REQUEST_DATE);
                datePicker.show(getFragmentManager(), "Date");


            }
        });

        iBtnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a photo file.
                //start a photo take activity.

                File photoFile = null;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager())!=null) {
                    try {
                        photoFile = createFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Sorry, can't create a picture", Toast.LENGTH_SHORT).show();
                    }
                    if (photoFile!=null){

                        //latest uses for Android N.
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.android.frankthirteen.timetracker",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                        startActivityForResult(intent, REQUEST_PHOTO);
                    }
                }
            }
        });


        return rootView;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case REQUEST_DATE:
                Date date = (Date) data.getSerializableExtra(Tracker.EXTRA_DATE);

                tracker.setEndDate(date);
                trEndDate.setText(FormatUtils.formatDate(date));
                break;
            case REQUEST_PHOTO:

                galleryAddPhoto();
                Bitmap bitmap = PictureUtils.getThumbnail(trPhoto,currentPhotoPath);
                trPhoto.setImageBitmap(bitmap);
                tracker.setPhotoPath(currentPhotoPath);

                //store the photo, get a thumbnail.
                //change the ImageView.
                //set tracker's photo attribute.

                break;
        }
    }

    private void initialView(View rootView) {
        trTitle = (EditText) rootView.findViewById(R.id.tracker_title);
        trContent = (EditText) rootView.findViewById(R.id.tracker_content);
        trComment = (EditText) rootView.findViewById(R.id.tracker_comment);
        trTimeCost = (EditText) rootView.findViewById(R.id.tracker_plan_time);

        trPhoto = (ImageView) rootView.findViewById(R.id.tracker_photo);

        trEndDate = (TextView) rootView.findViewById(R.id.tracker_end_date);

        iBtnTakePhoto = (ImageButton) rootView.findViewById(R.id.tracker_add_photo);
        btnChooseDate = (Button) rootView.findViewById(R.id.tracker_choose_date);

        boolean hasCamera = getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (!hasCamera){
            iBtnTakePhoto.setVisibility(View.GONE);
        }
    }

    private File createFile() throws IOException{

        String photoName = tracker.getId().toString();
        String photoFileName = "JPEG_" + photoName;
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(photoFileName,"jpt",dir);

        currentPhotoPath = image.getAbsolutePath();
        return image;

    }

    private void galleryAddPhoto(){
        Intent mediaSendIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaSendIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaSendIntent);
    }

}
