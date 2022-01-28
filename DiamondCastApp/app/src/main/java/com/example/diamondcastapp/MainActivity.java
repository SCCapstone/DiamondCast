package com.example.diamondcastapp;
/*import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;*/
import com.stripe.android.PaymentConfiguration;
import android.app.Application;

public class MainActivity extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        PaymentConfiguration.init(
                getApplicationContext(),
                ""//TODO stripe key
        );
    }
}
