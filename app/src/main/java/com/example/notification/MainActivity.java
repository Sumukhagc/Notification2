package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
   public static final String channelId = "channel1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_main );
        Button button=findViewById( R.id.button );

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel( "Noti","Noti", NotificationManager.IMPORTANCE_DEFAULT );
            NotificationManager manager=getSystemService( NotificationManager.class );
            manager.createNotificationChannel(channel);
        }
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( MainActivity.this, Notification.class);
                PendingIntent contentIntent=PendingIntent.getActivity( MainActivity.this,0,intent,0 );

                Bitmap largeIcon =BitmapFactory.decodeResource( getResources(),R.drawable.girl);
                Bitmap newOne=getCircleBitmap(largeIcon);
                NotificationCompat.Builder builder = new NotificationCompat.Builder( MainActivity.this ,"Noti")
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Welcome!")
                        .setLargeIcon( newOne  )
                        .addAction( R.drawable.icon,"SIGNUP",contentIntent )
                        .setAutoCancel( true )
                        .setFullScreenIntent( contentIntent,true )

                        .setColor( ContextCompat.getColor(MainActivity.this, R.color.colorPrimary))
                        .setStyle( new NotificationCompat.BigTextStyle().bigText("Complete your paperless account setup to start investing in less than 2 mins.") )
                        .setContentIntent( contentIntent );

                NotificationManagerCompat managerCompat=NotificationManagerCompat.from( MainActivity.this );
                managerCompat.notify( 1,builder.build() );

            }

        } );

    }
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode( PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }
}
