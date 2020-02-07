package com.example.uni;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni.util.Markalive;
import com.example.uni.util.PersonInfo;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.uni.code.UrlCode.COM_PEOPLE_PUSH;

public class Com_Push_PeopleActivity extends AppCompatActivity {
    private Dialog bottomDialog;
    private Button push,cameraorphoto,delete;
    private EditText push_text;
    private File camera_outputImage;
    public static final  int TAKE_PHOTO   = 1;
    public static final  int CHOOSE_PHOTO = 2;
    private ImageView picture;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_push);
        //camera_outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        push       =  (Button)findViewById(R.id.push_push);
        delete     =  (Button)findViewById(R.id.delete);
        delete.setAlpha(0.0f);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delete.getAlpha()==1.0f){
                     picture.setAlpha(0.0f);
                }
            }
        });

        picture = (ImageView)findViewById(R.id.picture);
        picture.setAlpha(0.0f);
        cameraorphoto    =  (Button)findViewById(R.id.push_cameraorphoto);
        push_text =  (EditText)findViewById(R.id.push_text);
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com_push();
                //Intent intent = new Intent(Com_PushActivity.this,UseView_PageOneActivity.class);
               // startActivity(intent);
            }
        });
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picture.getAlpha()!=0.0f) {
                    initPopWindow(v);
                }
                //picture.setAlpha();
            }
        });

        cameraorphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.show();
            }
        });
        bottomDialog = new Dialog(Com_Push_PeopleActivity.this,R.style.BottomDialogStyle);
        //填充对话框的布局
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_dialog,null);
        TextView fromphoto  = (TextView)view.findViewById(R.id.from_photo);
        TextView fromcamera = (TextView)view.findViewById(R.id.from_camera);
        TextView cancel     = (TextView)view.findViewById(R.id.cancel);
        fromphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请读写SD卡的权限
                if(ContextCompat.checkSelfPermission(Com_Push_PeopleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Com_Push_PeopleActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    com_fromphoto();
                }
                bottomDialog.dismiss();
                //Toast.makeText(Com_PushActivity.this,"you click me",Toast.LENGTH_LONG).show();
            }
        });
        fromcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com_fromcamera();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();

            }
        });
        //将布局设置给Dialog
        bottomDialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialowWindow = bottomDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialowWindow.setGravity(Gravity.BOTTOM);
        //获取窗体的属性
        WindowManager.LayoutParams lp = dialowWindow.getAttributes();
        lp.width = (int)(getWindowManager().getDefaultDisplay().getWidth()*0.95);
        lp.y = 2;
        dialowWindow.setAttributes(lp);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                Log.d("Com_push","setssspicture");
                Log.d("Com_push","requestCode = " + requestCode + "Result_OK = "+RESULT_OK);
                //if(requestCode == RESULT_FIRST_USER){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        delete.setAlpha(1.0f);
                        picture.setAlpha(1.0f);
                        picture.setImageBitmap(bitmap);
                        Log.d("Com_push","setpicture");
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                //}
                bottomDialog.dismiss();
                break;
            case CHOOSE_PHOTO:
                //if(requestCode == 2){
                 if (Build.VERSION.SDK_INT>=19){
                     //4.4及以上使用这个方法处理图片
                     handleImageOnKitKat(data);
                 }else {
                     //4.4以下系统使用这个方法处理图片
                     handleImageBeforeKitKat(data);
                 }
                //}
                break;
            default:
                break;
        }
    }

    private void initPopWindow(View v){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.photo_preview_item_popip,null);
        ImageView  imageView = (ImageView)view.findViewById(R.id.image);

        final PopupWindow popupWindow =new PopupWindow(view , ViewGroup.LayoutParams.MATCH_PARENT,1200,true);
        imageView.setImageDrawable(picture.getDrawable());
        popupWindow.setAnimationStyle(R.anim.photo_preview);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        popupWindow.showAtLocation(getWindow().getDecorView(),Gravity.CENTER,0,0);//showAtLocation(view, Gravity.CENTER,0,0);
        bgAlpha(0.618f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                bgAlpha(1.0f);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 popupWindow.dismiss();
            }
        });
    }


    public void bgAlpha(float f){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = f;
        getWindow().setAttributes(layoutParams);
    }
    @TargetApi(19)
    private  void handleImageOnKitKat(Intent data){
       String imagePath = null;
       try{//直接返回要是没有try就null了
           Uri uri = data.getData();
           if(DocumentsContract.isDocumentUri(this,uri)){
               //如果是document型的Uri，则通过document的id处理
               String docId = DocumentsContract.getDocumentId(uri);
               if("com.android.providers.media.documents".equals(uri.getAuthority())){
                   String id = docId.split(":")[1];
                   String selection = MediaStore.Images.Media._ID + "=" +id;
                   imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
               }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                   Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                   imagePath = getImagePath(contentUri,null);
               }else if("content".equalsIgnoreCase(uri.getScheme())){
                   //如果是content类型的Uri，则使用普通方式处理
                   imagePath = getImagePath(uri,null);
               }else if("file".equalsIgnoreCase(uri.getScheme())){
                   //[scheme:][//authority][path][?query][#fragment]
                   //如果是file类型的Uri,直接获取图片路径即可
                   imagePath = uri.getPath();
               }
               displayImage(imagePath);
               camera_outputImage = new File(imagePath);
               /*
               camera_outputImage = new File(getExternalCacheDir(),"output_image.jpg");
               try{
                   if(camera_outputImage.exists()){
                       camera_outputImage.delete();
                   }
                   camera_outputImage.createNewFile();
               }catch (IOException e){
                   e.printStackTrace();
               }
               */
               Log.d("CPA: ",imagePath);
             //  data.putExtra(MediaStore.EXTRA_OUTPUT,imagePath);
              /*
               FileOutputStream fileOutputStream = null;

               try{
                   fileOutputStream = new FileOutputStream(camera_outputImage);

               }catch (FileNotFoundException e){
                   e.printStackTrace();
               }*/
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    private void handleImageBeforeKitKat(Intent data){
          Uri uri = data.getData();
          String imagePath = getImagePath(uri,null);
          displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null ){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            delete.setAlpha(1.0f);
            picture.setAlpha(1.0f);
            picture.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_LONG).show();
        }
    }



    public void com_push(){
       //String url = "http://118.25.42.59:8000/com_people";
        if(push_text.getText().toString()==null||push_text.getText().toString().length()==0){
            Toast.makeText(Com_Push_PeopleActivity.this,"发表内容不能为空！",Toast.LENGTH_LONG).show();
            return;
        }
        MultipartBody multipartBody;
        String markalive_stu = LitePal.findFirst(Markalive.class).getStu_number();
        String stu_number = LitePal.where("StudentNum = ? ",markalive_stu).findFirst(PersonInfo.class).getStudentNum();
        String nickname = LitePal.where("StudentNum = ? ",markalive_stu).findFirst(PersonInfo.class).getNickname();
        if(nickname==null||nickname.length()==0){
            nickname = " ";
        }
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        String time = c.get(Calendar.YEAR) +"/"+ month +"/"+ c.get(Calendar.DATE)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
        String content = push_text.getText().toString();

        if (camera_outputImage!=null) {
            RequestBody filebody = RequestBody.create(MediaType.parse("application/octet-stream"), camera_outputImage);
            multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image","big.jpg",filebody)
                .addFormDataPart("stu_number",stu_number)
                .addFormDataPart("time",time)
                .addFormDataPart("content",content)
                .addFormDataPart("nickname",nickname)
                .build();
        }
        else {
             multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("stu_number",stu_number)
                    .addFormDataPart("time",time)
                    .addFormDataPart("content",content)
                    .addFormDataPart("nickname",nickname)
                    .build();
        }

        Request request = new Request.Builder()
                .url(COM_PEOPLE_PUSH)
                .post(multipartBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50,TimeUnit.SECONDS)
                .writeTimeout(50,TimeUnit.SECONDS)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(Com_Push_PeopleActivity.this,"提交失败！",Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if (responseData.equals("success")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(Com_Push_PeopleActivity.this,"提交成功！",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }else if(responseData.equals("fail")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(Com_Push_PeopleActivity.this,"提交失败！",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            }
        });
    }

    public void com_fromphoto(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode ,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1 :
                if(grantResults.length> 0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    com_fromphoto();
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_LONG).show();
                }
                break;
                default:
                    break;
        }
    }
    public void com_fromcamera(){
        camera_outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(camera_outputImage.exists()){
                camera_outputImage.delete();
            }
            camera_outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri = FileProvider.getUriForFile(Com_Push_PeopleActivity.this,"disguiser99",camera_outputImage);
            Log.d("Com_push",imageUri.toString());
        }else{
            imageUri = Uri.fromFile(camera_outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }
}

//bottomDialog
//https://www.jianshu.com/p/5b813fa204dc