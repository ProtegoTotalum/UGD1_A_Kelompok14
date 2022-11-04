package com.grayfien.testugd1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.grayfien.testugd1.databinding.ActivityRegisterBinding
import com.grayfien.testugd1.package_room.Pasien
import com.grayfien.testugd1.package_room.PasienDB
import com.grayfien.testugd1.package_room.User
import com.grayfien.testugd1.package_room.UserDB
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {

    private val CHANNEL_ID_1 = "channel_notification_01"
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: UserDB
    private val notificationId1 = 105

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        createNotificationChannel()

        db = Room.databaseBuilder(applicationContext,UserDB::class.java,"user-db").build()
        binding!!.btnRegister.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    User(0, inputNama.text.toString(),
                        inputUsername.text.toString(),
                        inputPassword.text.toString(),
                        inputEmail.text.toString(),
                        inputTanggalLahir.text.toString(),
                        inputTelp.text.toString())
                )
                finish()
                sendNotification1()
            }

        }

        binding.btnCancelRegister.setOnClickListener {
            val cancel = Intent(this, MainActivity::class.java)
            startActivity(cancel)
        }

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification1(){

        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val broadcastIntent : Intent = Intent( this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", "Selamat Anda Berhasil Register")
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentText("Selamat Anda Berhasil Register")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.YELLOW)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    // Provide the bitmap to be used as the payload for the BigPicture notification.
                    .bigPicture(BitmapFactory.decodeResource(resources,R.drawable.meds))
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }
    }
}

/*
    private lateinit var vNama: TextInputLayout
    private lateinit var vEmail: TextInputLayout
    private lateinit var vNoTelp: TextInputLayout
    private lateinit var vUsername: TextInputLayout
    private lateinit var vPassword: TextInputLayout
    private lateinit var vTglLahir: TextInputLayout
    private lateinit var namaInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var noTelpInput: TextInputEditText
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var tanggalLahirInput: TextInputEditText

 */


// setContentView(R.layout.activity_register)

/*
namaInput = findViewById(R.id.inputNama)
emailInput = findViewById(R.id.inputEmail)
noTelpInput = findViewById(R.id.inputTelp)
usernameInput = findViewById(R.id.inputUsername)
passwordInput = findViewById(R.id.inputPassword)
tanggalLahirInput = findViewById(R.id.inputTanggalLahir)

vNama = findViewById(R.id.layNama)
vEmail = findViewById(R.id.layEmail)
vNoTelp = findViewById(R.id.layTelp)
vUsername = findViewById(R.id.layUsername)
vPassword = findViewById(R.id.layPassword)
vTglLahir = findViewById(R.id.layTanggalLahir)

btnRegister = findViewById(R.id.btnRegister)


btnRegister.setOnClickListener {
    val intent = Intent(this, MainActivity::class.java)
    val mBundle = Bundle()

    val nama : String = vNama.getEditText()?.getText().toString()
    val email : String = vEmail.getEditText()?.getText().toString()
    val noTelp : String = vNoTelp.getEditText()?.getText().toString()
    val tglLahir : String = vTglLahir.getEditText()?.getText().toString()
    val username : String = vUsername.getEditText()?.getText().toString()
    val password : String = vPassword.getEditText()?.getText().toString()


    if(nama.isEmpty()) {
        namaInput.setError("Nama Tidak Boleh Kosong")
    }
    if(email.isEmpty()){
        emailInput.setError("Email Tidak Boleh Kosong")
    }
    if(noTelp.isEmpty()) {
        noTelpInput.setError("Nomor Telepon Tidak Boleh Kosong")
    }
    if(username.isEmpty()) {
        usernameInput.setError("Username Tidak Boleh Kosong")
    }
    if(password.isEmpty()) {
        passwordInput.setError("Password Tidak Boleh Kosong")
    }
    if(tglLahir.isEmpty()){
        tanggalLahirInput.setError("Tanggal Lahir Tidak Boleh Kosong")
    }else{
        mBundle.putString("nama", vNama.getEditText()?.getText().toString())
        mBundle.putString("email", vEmail.getEditText()?.getText().toString())
        mBundle.putString("noTelp", vNoTelp.getEditText()?.getText().toString())
        mBundle.putString("username", vUsername.getEditText()?.getText().toString())
        mBundle.putString("password", vPassword.getEditText()?.getText().toString())
        mBundle.putString("tanggalLahir", vTglLahir.getEditText()?.getText().toString())

        intent.putExtras(mBundle)
        startActivity(intent)
    }
}

 */


