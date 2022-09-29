package com.grayfien.testugd1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import com.grayfien.testugd1.databinding.ActivityRegisterBinding
import com.grayfien.testugd1.databinding.ActivityUserBinding
import com.grayfien.testugd1.package_room.PasienDB
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private lateinit var shareP: Preference
    private lateinit var binding: ActivityUserBinding
    private lateinit var db: PasienDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        db = Room.databaseBuilder(applicationContext,PasienDB::class.java,"pasien-db").build()
        val view = binding.root
        setContentView(view)
        shareP = Preference(this)



        val nama = shareP.getUser()?.name
        val email = shareP.getUser()?.email
        val tglLahir = shareP.getUser()?.tglLahir
        val noTelp = shareP.getUser()?.noTelp


        binding.editNama.setText(nama)
        binding.editEmail.setText(email)
        binding.editTglLahir.setText(tglLahir)
        binding.editNoTelp.setText(noTelp)


        btnEditProfile.setOnClickListener{
            val intent = Intent(this, EditUserActivity::class.java)
            startActivity(intent)
        }





    }
}