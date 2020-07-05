package tdc.edu.vn.qlsv.GiaoDien;

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import tdc.edu.vn.qlsv.R;

public class DangNhap extends AppCompatActivity {
    EditText edit_user,edit_password;
    Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();
    }
    private void setControl(){
        edit_user=findViewById(R.id.edit_username);
        edit_password=findViewById(R.id.edit_password);
        bt_login=findViewById(R.id.bt_login);
    }
    private boolean isSuccess(String textUser,String textPassword){
        String user="1";
        String password="1";

        return user.equals(textUser) && password.equals(textPassword);
    }
    private void setEvent(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getTextUser=edit_user.getText().toString();
                String getPassUser=edit_password.getText().toString();
                if(!isSuccess(getTextUser,getPassUser)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DangNhap.this);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Đăng nhập thất bại mời nhập lại");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }
                else {
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("username",getTextUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }
}