package com.capstone.smaily.ui.parent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capstone.smaily.R
import com.capstone.smaily.databinding.ActivityRegisterParentBinding
import com.capstone.smaily.viewmodel.MainViewModel

class RegisterParentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterParentBinding
    private lateinit var registerViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.parent)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        registerViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            btnRegister.setOnClickListener { register() }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun register() {
        binding.apply {
            val txtName = etName.text.toString()
            val txtEmail = etEmail.text.toString()
            val txtPassword = etPassword.text.toString()

            if (txtName.isEmpty()){
                etName.error = resources.getString(R.string.must_be_filled)
                return
            }
            if (txtEmail.isEmpty()){
                etEmail.error = resources.getString(R.string.must_be_filled)
                return
            }
            if (txtPassword.isEmpty()){
                etPassword.error = resources.getString(R.string.must_be_filled)
                return
            }

            //code untuk input ke db
            with(registerViewModel){
                registerParent(txtName, txtEmail, txtPassword)
                isLoading.observe(this@RegisterParentActivity) { showLoading(it) }
                isIntent.observe(this@RegisterParentActivity) {
                    if (it){
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@RegisterParentActivity, LoginParentActivity::class.java))
                            finish()
                        }, 2000)
                    }
                }
                message.observe(this@RegisterParentActivity) { showToast(it) }
            }
            //end code
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(state : Boolean){
        binding.bars.visibility = if(state) View.VISIBLE else View.GONE
    }
}
