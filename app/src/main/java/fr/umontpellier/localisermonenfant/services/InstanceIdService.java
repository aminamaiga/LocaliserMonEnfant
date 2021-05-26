package fr.umontpellier.localisermonenfant.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.WelcomeActivity;
import fr.umontpellier.localisermonenfant.models.Token;
import fr.umontpellier.localisermonenfant.networks.businesses.BussnessServiceToken;

public class InstanceIdService extends FirebaseMessagingService {
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6578;
    Context context;
    private static final String CHANNEL_ID = "notification_parent";
    private static final String TAG = "TAG";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("NEW_TOKEN", token);
        BussnessServiceToken bussnessServiceToken = new BussnessServiceToken(context);
        bussnessServiceToken.initializeAuthentificatedCommunication();
        bussnessServiceToken.save(new Token("", new Date(), token));
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        final String title = remoteMessage.getData().get("title");
        final String message = remoteMessage.getData().get("body");

        createNotificationChannel();
        showNotifications(title, message);
    }

    private void showNotifications(String title, String msg) {
        Intent i = new Intent(this, WelcomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText(msg)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Information sur la position de votre enfant ")
                        .setSummaryText("Localiser votre enfant")
                        .bigText("Vous venez de recevoir une notification sur les déplacements de votre enfant. Vous pouvez consulter la liste des notifications sur le menu principal"))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_location_on_24px)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setLights(ContextCompat.getColor(
                        getApplicationContext(), R.color.black), 1000, 1000)
                .setVibrate(new long[]{800, 800, 800, 800})
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.default_notification_channel_id);
            String description = "Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}