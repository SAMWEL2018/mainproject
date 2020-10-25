package com.example.mainproject.fragments;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mainproject.MyModels.uploadinfo;
import com.example.mainproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import io.paperdb.Paper;

import static android.app.Activity.RESULT_OK;
import static com.example.mainproject.Activities.supplier_login.SUP;

/**
 * A simple {@link Fragment} subclass.
 */
public class add_sup_item extends Fragment {
    Button photo,post;
    EditText name,price,contact;
    ImageView imageView;
    Intent intent;
    private  static final  int CAMERA_REQUEST= 123;

    Uri filepath;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    int CODE=1;
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    String email;

    public add_sup_item() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentActivity actionBar=getActivity();
        actionBar.setTitle("Supplier Page");

        View view= inflater.inflate(R.layout.fragment_add_item,container,false);


        photo= view.findViewById(R.id.launch_photo);
        post= view.findViewById(R.id.post);
        name= view.findViewById(R.id.name);
        price= view.findViewById(R.id.price);
        imageView= view.findViewById(R.id.image_post);
        contact=view.findViewById(R.id.contact);
        email= Paper.book().read(SUP);

        progressDialog=new ProgressDialog(getContext());

        databaseReference= FirebaseDatabase.getInstance().getReference("Images");
        storageReference= FirebaseStorage.getInstance().getReference();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose"),CODE);

            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask !=null && uploadTask.isInProgress()){
                    Toast.makeText(getContext(), "Upload in Progress", Toast.LENGTH_SHORT).show();
                    
                }
                else {
                    progressDialog.setMessage("Uploading Image");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    UploadImage();
                }

            }
        });






        return view;
    }
    public String Getfile_extension(Uri uri){
        ContentResolver contentResolver=getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void UploadImage() {
        if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(price.getText().toString()) || TextUtils.isEmpty(contact.getText().toString())){
            progressDialog.dismiss();
            name.setError("Field Cannot be empty");
            price.setError("Field cannot be empty");
            contact.setError("Field cannot be Empty");
            return;
        }
        if (TextUtils.isEmpty(name.getText().toString())){
            progressDialog.dismiss();
            name.setError("Field Cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(price.getText().toString().trim())){
            progressDialog.dismiss();
            price.setError("Field cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(contact.getText().toString().trim())){
            progressDialog.dismiss();
            contact.setError("Field cannot be Empty");
            return;
        }



        if (filepath!=null){
            final StorageReference storageReference1=storageReference.child(System.currentTimeMillis()+"."+Getfile_extension(filepath));
            uploadTask= (UploadTask) storageReference1.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String Imagename=name.getText().toString().trim();
                                    String cost="Ksh. "+ price.getText().toString().trim();
                                    String phone=contact.getText().toString().trim();
                                    String ImageId=databaseReference.push().getKey();


                                    uploadinfo uploadinfo=new uploadinfo(Imagename,uri.toString(),cost,phone,email,ImageId);

                                    databaseReference.child(ImageId).setValue(uploadinfo);
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                                    name.setText("");
                                    price.setText("");
                                    contact.setText("");


                                }
                            });



                        }
                    });
        }
    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==CODE){
            if (resultCode==RESULT_OK && data!=null && data.getData()!=null){
                filepath=data.getData();

                try {
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filepath);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


}
