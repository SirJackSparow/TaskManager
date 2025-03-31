package com.example.feature.presentation.securescreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE
import android.hardware.biometrics.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED
import android.hardware.biometrics.BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
import android.hardware.biometrics.BiometricManager.BIOMETRIC_SUCCESS
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.biometric.BiometricAuthenticator


@Composable
fun SecureScreen(vm: SecureViewModel = hiltViewModel(), context: AppCompatActivity) {
    val biometricAuthenticator = BiometricAuthenticator(context)
    val secureData by vm.state.collectAsState()
    var input by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        vm.getSecureData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                when (val auth = biometricAuthenticator?.canAuthenticate()) {
                    BIOMETRIC_SUCCESS -> {
                        biometricAuthenticator.showBiometricPrompt(
                            title = "Authenticate",
                            description = "Use biometric to authenticate",
                            onSuccess = {
                                vm.saveSecureData(input)
                                Toast.makeText(context, "Data Saved Securely!", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            onFailure = { msg ->
                                Log.e("error", msg )
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                    BIOMETRIC_ERROR_NO_HARDWARE -> Toast.makeText(
                        context,
                        "Biometric no Hardware available.",
                        Toast.LENGTH_SHORT
                    ).show()

                    BIOMETRIC_ERROR_HW_UNAVAILABLE -> Toast.makeText(
                        context,
                        "Biometric error hardware unavailable.",
                        Toast.LENGTH_SHORT
                    ).show()

                    BIOMETRIC_ERROR_NONE_ENROLLED -> {
                        Toast.makeText(
                            context,
                            "Biometric error none enrolled.",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Open the biometric enrollment screen
                        val enrollIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                putExtra(
                                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BiometricManager.Authenticators.BIOMETRIC_STRONG
                                )
                            }
                        } else {
                            Intent(Settings.ACTION_SECURITY_SETTINGS)
                        }
                        context.startActivity(enrollIntent)
                    }

                    else -> {
                        Log.e("error", auth.toString() )
                        Toast.makeText(context, "Biometric error.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text(secureData ?: "Null")
        }
    }

    Text(text = "Saved Secure Data: ${secureData ?: "None"}", modifier = Modifier.padding(16.dp))

}