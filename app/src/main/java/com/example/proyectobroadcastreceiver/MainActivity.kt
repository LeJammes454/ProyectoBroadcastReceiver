package com.example.proyectobroadcastreceiver

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectobroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    //Condifguracion del binding para poder acceder a las variables que tenemos en el layout principal
    private lateinit var binding: ActivityMainBinding

    //Instanciando la clase CallReceiver para porder usarla desde aqui
    private lateinit var callReceiver: CallReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Configurar el botón para guardar los datos
        binding.btnGuardar.setOnClickListener {
            val phoneNumber = binding.etNumero.text.toString()
            val message = binding.etMensaje.text.toString()

            val intent = Intent(this, MyService::class.java)
            startService(intent)

            // Guardar los datos en las preferencias compartidas
            val sharedPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.putString("phoneNumber", phoneNumber)
            editor.putString("message", message)
            editor.apply()


            //Mostramnos pequeño mensaje para aviasrle al usuario que gaurdamos los datos
            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
        }

        //Nos suscribrimos al CallReceiver
        callReceiver = CallReceiver()
    }
    override fun onStart() {
        super.onStart()

        // Registrar el BroadcastReceiver
        val intentFilter = IntentFilter()
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(callReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        // Desregistrar el BroadcastReceiver
        unregisterReceiver(callReceiver)
    }
}