package com.grayfien.testugd1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.grayfien.testugd1.databinding.ActivityPasienBinding
import com.grayfien.testugd1.fragment.FragmentHome
import com.grayfien.testugd1.fragment.FragmentPasien


class PasienActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPasienBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasienBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDataFragment()

        binding.txtCari.setOnKeyListener(View.OnKeyListener{_, keyCode, event->if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
            showDataFragment()
            return@OnKeyListener true
        }
            false
        })

        binding.btnAdd.setOnClickListener{
            startActivity(Intent(this,AddPasienActivity::class.java))
        }
        binding.btnCancel.setOnClickListener{
            startActivity(Intent(this,FragmentHome::class.java))
        }
    }

    fun showDataFragment (){
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction =
            mFragmentManager.beginTransaction()
        val mFragment = FragmentPasien()
        val textCari = binding.txtCari.text
        val mBundle = Bundle()
        mBundle.putString("cari", textCari.toString())
        mFragment.arguments = mBundle
        mFragmentTransaction.replace(R.id.fl_data, mFragment).commit()
    }
}
