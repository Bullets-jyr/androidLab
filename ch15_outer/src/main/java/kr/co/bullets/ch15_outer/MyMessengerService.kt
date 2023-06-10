package kr.co.bullets.ch15_outer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import java.lang.Exception

class MyMessengerService : Service() {
    // Activity에서 넘어오는 데이터를 받는 용도
    lateinit var messenger: Messenger

    // Activity에 데이터를 전달하는 역할
    lateinit var replyMessenger: Messenger

    // MediaPlayer
    lateinit var player: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    // Activity로부터 데이터가 전달이 되었을 때, 데이터를 받아서 처리할 수 있는 Handler
    inner class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler(
        Looper.getMainLooper()
    ) {
        // msg: Message - Activity에서 전달받은 데이터
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                10 -> {
                    // 메시지를 받은 후에 답장을 보내야 할 경우에 사용할 메신저를 설정하는 역할
                    replyMessenger = msg.replyTo
                    if (!player.isPlaying) {
                        player = MediaPlayer.create(this@MyMessengerService, R.raw.music)
                        try {
                            val replyMsg = Message()
                            replyMsg.what = 10
                            val replyBundle = Bundle()
                            replyBundle.putInt("duration", player.duration)
                            replyMsg.obj = replyBundle
                            replyMessenger.send(replyMsg)

                            player.start()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                20 -> {
                    if (player.isPlaying) {
                        player.stop()
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        messenger = Messenger(IncomingHandler(this))
        // MyMessengerService를 바인딩 시킨 곳에 IncomingHandler객체가 전달되고, Messenger를 통해서 데이터를 넘기면
        // override fun handleMessage(msg: Message)에서 처리를 합니다.
        return messenger.binder
    }
}