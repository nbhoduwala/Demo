package com.harun.testapp

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            if(MainApplication().isAppInForground){
                showDialog()
            }else{
                notification("Welcome", "Notification")
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private fun showDialog(){
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Notification Received?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                    dialog, id -> dialog.dismiss()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Alert")
        // show alert dialog
        alert.show()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("AccessToken", "$token")

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("fcmToken", token)
        editor.apply()
    }

    private fun notification(name: String, title: String) {
        // pendingIntent is an intent for future use i.e after
        // the notification is clicked, this intent will come into action
        var intent = Intent(this@MyFirebaseMessagingService, MainActivity::class.java)

        // FLAG_UPDATE_CURRENT specifies that if a previous
        // PendingIntent already exists, then the current one
        // will update it with the latest intent
        // 0 is the request code, using it later with the
        // same method again will get back the same pending
        // intent for future reference
        // intent passed here is to our afterNotification class
        val pendingIntent =
            PendingIntent.getActivity(this, 121, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout
        // val contentView = RemoteViews(packageName, R.layout.activity_after_notification)

        // checking if android version is greater than oreo(API 26) or not
       val sound = Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.sound);//Here is FILE_NAME is the name of file that you want to play
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, "", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            //val cal = Calendar.getInstance().time.time

            val time = System.currentTimeMillis()


            builder = Notification.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(name + " is Online")
                .setAutoCancel(true)
                .setWhen(time)
                .setSound(sound)
                .setColor(ContextCompat.getColor(baseContext, R.color.black))
                .setShowWhen(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.mipmap.ic_launcher
                    )
                )
                .setContentIntent(pendingIntent)
        } else {
            val time = System.currentTimeMillis()

            //val cal = Calendar.getInstance().time.time
            builder = Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(name + " is Online")
                .setAutoCancel(true)
                .setWhen(time)
                .setSound(sound)
                .setColor(ContextCompat.getColor(baseContext, R.color.black))
                .setShowWhen(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.mipmap.ic_launcher
                    )
                )
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1, builder.build())
    }
}