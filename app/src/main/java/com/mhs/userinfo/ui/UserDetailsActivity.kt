package com.mhs.userinfo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.mhs.userinfo.R
import com.mhs.userinfo.databinding.ActivityUserDetailsBinding
import com.mhs.userinfo.utils.DataStatus
import com.mhs.userinfo.utils.isVisible
import com.mhs.userinfo.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        // Check if the Intent has extra data with the key "EXTRA_MESSAGE"
        if (intent.hasExtra("ID")) {
            // Retrieve the string from the Intent
            id = intent.getStringExtra("ID")
            getUserDetails(id)
        }
    }

    private fun getUserDetails(id: String?) {
        lifecycleScope.launch {
            binding.apply {
                if (id != null) {
                    viewModel.getUserDetails(id)
                }
                viewModel.userDetails.observe(this@UserDetailsActivity){ it ->
                    when(it.status){
                        DataStatus.Status.LOADING ->{
                            pBarLoading.isVisible(true, mainLayout)
                        }
                        DataStatus.Status.SUCCESS ->{
                            it.data?.let { it ->
                                pBarLoading.isVisible(false, mainLayout)
                                imgActor.load(it[0].image) {
                                    crossfade(true)
                                    crossfade(500)
                                    placeholder(R.drawable.person_icon)
                                    error(R.drawable.person_icon)
                                }

                                txtName.text = it[0].name.takeIf { it.isNotBlank() } ?: "- - - - -"
                                txtSpecies.text = it[0].species.takeIf { it.isNotBlank() } ?: "- - - - -"
                                txtGender.text = it[0].gender.takeIf { it.isNotBlank() } ?: "- - - - -"
                                txtHouse.text = it[0].house.takeIf { it.isNotBlank() } ?: "- - - - -"
                                if (it[0].dateOfBirth == "" || it[0].dateOfBirth == null)
                                    txtDateOfBirth.text = "- - - - -"
                                else
                                    txtDateOfBirth.text = it[0].dateOfBirth

                                if (it[0].yearOfBirth == null || it[0].yearOfBirth == 0)
                                    txtYearOfBirth.text = "- - - - -"
                                else
                                    txtYearOfBirth.text = it[0].yearOfBirth.toString()

                                txtAncestry.text = it[0].ancestry.takeIf { it.isNotBlank() } ?: "- - - - -"
                                txtEyeColour.text = it[0].eyeColour.takeIf { it.isNotBlank() } ?: "- - - - -"
                                txtHairColour.text = it[0].hairColour.takeIf { it.isNotBlank() } ?: "- - - - -"
                                txtPatronus.text = it[0].patronus.takeIf { it.isNotBlank() } ?: "- - - - -"
                                txtActor.text = it[0].actor.takeIf { it.isNotBlank() } ?: "- - - - -"

                            }
                        }
                        DataStatus.Status.ERROR ->{
                            pBarLoading.isVisible(true, mainLayout)
                            Toast.makeText(this@UserDetailsActivity, "There is something wrong!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}