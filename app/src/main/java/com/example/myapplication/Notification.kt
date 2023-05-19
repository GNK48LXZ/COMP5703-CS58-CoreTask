
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.ViewModel
import com.example.myapplication.R


const val KEY_MESSAGE = "Notification_Message"
const val KEY_NOTIFICATION_ID = "Notification_Id"

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            val msg = getStringExtra(KEY_MESSAGE)
            //msg?.let { context?.showToast(msg) }
            val id = getIntExtra(KEY_NOTIFICATION_ID, 0)
            context?.cancelNotification(id) // 根据需求决定要不要取消
        }
    }
}



class NotificationTestViewModel: ViewModel() {
    var notification: Notification? = null

    private fun buildNotification(context: Context, title: String, message: String) {
        val clickIntent = Intent(context, Notification::class.java)
        notification = context.buildNotification(
            id = 1,
            title = title,
            message = message,
            action = "View this offer",
            actionMessage = "点击了按钮",
            visibility = VISIBILITY_PUBLIC,
            activityIntent = clickIntent,
        )
    }

    fun showNotification(context: Context, title: String, message: String) {
        buildNotification(context, title, message)
        notification?.show(context)
    }

    fun updateNotification(context: Context, titleNew: String, messageNew: String) {
        notification?.update(context, titleNew, messageNew)
    }
}

const val MAIN_CHANNEL_ID = "MainChannel ID"
const val MAIN_CHANNEL = "MainChannel"

fun Context.buildNotification(
    id: Int,
    title: String,
    message: String,
    action: String? = null,
    actionMessage: String? = null,
    visibility: Int = VISIBILITY_PUBLIC,
    activityIntent: Intent? = null,
    isDeepLink: Boolean = false
): Notification {
    val notification = Notification(id, title, message, action, actionMessage,
        visibility, activityIntent,isDeepLink)
    notification.builder = notification.builder(this)
    notification.manager = getNotificationManager()
    return notification
}

data class Notification(
    val id: Int,
    var title: String,
    var message: String,
    var action: String? = null,
    var actionMessage: String? = null,
    var visibility: Int = VISIBILITY_PUBLIC,
    var activityIntent: Intent? = null,
    val isDeepLink: Boolean = false,
    var builder: NotificationCompat.Builder? = null,
    var manager: NotificationManagerCompat? = null
)

fun Notification.show(
    context: Context,
): Notification {
    builder?.let { if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return this
    }
        manager?.notify(id, it.build()) }
    return this
}

fun Notification.update(
    context: Context,
    titleNew: String? = null,
    messageNew: String? = null,
    action1: String? = null,
    visibleType: Int = VISIBILITY_PUBLIC
): Notification  {
    titleNew?.let { title = titleNew }
    messageNew?.let { message = messageNew}
    action1?.let { action = action1  }
    if (visibleType != visibility) visibility = visibleType
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return this
    }
    manager?.notify(id, builder(context).build())
    return this
}

fun Notification.builder(context: Context): NotificationCompat.Builder {

    val builder = NotificationCompat.Builder(context, MAIN_CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setPriority(PRIORITY_HIGH)
        .setVisibility(visibility)
        .setAutoCancel(true)
        .setFullScreenIntent(null, true)
    if (visibility == VISIBILITY_PRIVATE) {
        builder.setPublicVersion(
            Builder(context, MAIN_CHANNEL_ID)
                .setContentTitle("收到一条新的消息")
                .setContentText("请解锁屏幕后查看！")
                .build()
        )
    }
    val flg = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
    action?.let {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(KEY_MESSAGE, actionMessage)
            putExtra(KEY_NOTIFICATION_ID, id)
        }
        PendingIntent.getBroadcast(context, 0, intent, flg)
    }?.let { builder.addAction(0, action, it) }

    if (isDeepLink) {
        activityIntent?.let {
            TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(it)
                getPendingIntent(1, flg)
            }
        }?.let { builder.setContentIntent(it) }
    } else {
        activityIntent?.let { PendingIntent.getActivity(context, 1, it, flg) }
            ?.let { builder.setContentIntent(it) }
    }
    return builder
}

fun Context.getNotificationManager(): NotificationManagerCompat {
    val notificationManager = NotificationManagerCompat.from(applicationContext)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(MAIN_CHANNEL_ID, MAIN_CHANNEL,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.setAllowBubbles(true)
        notificationManager.createNotificationChannel(channel)
    }
    return notificationManager
}

fun Context.cancelNotification(id: Int) = getNotificationManager().cancel(id)



@Composable
fun NotificationTest(viewModel: NotificationTestViewModel= androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.showNotification(context,"外卖提醒", "您好，您的外卖到了！")
        }) {
            Text(text = "创建一个新通知")
        }
        Button(onClick = {
            viewModel.updateNotification(context,"订单提醒", "您有一条新的外卖订单，请及时接单！")
        }) {
            Text(text = "更新通知")
        }
    }
}

@Composable
fun No(viewModel: NotificationTestViewModel= androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    viewModel.showNotification(context,"Notification", "You received an offer!")
}