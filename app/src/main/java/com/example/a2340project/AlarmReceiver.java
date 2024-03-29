package com.example.a2340project;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Firing alarm", Toast.LENGTH_SHORT).show();

        Bundle extras = intent.getExtras();
        String title = extras.getString("TaskTitle");
        String timeRemaining = extras.getString("TimeRemaining");
        String sourceUser = extras.getString("sourceUser");

        Intent launchAppIntent = new Intent(context, MainActivity.class);
        launchAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchAppIntent.putExtra(LoginActivity.USERNAME_KEY, sourceUser);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.NOTIF_CHANNEL_ID)
                .setSmallIcon(R.drawable.notiflogo)
                .setContentText(title + "\n" + "Time Remaining: " + timeRemaining)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("Notifications", "Missing permission POST_NOTIFICATIONS");
            return;
        }
        notificationManagerCompat.notify(666, builder.build());
    }
}
