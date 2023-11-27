package com.example.askingmanypermissions
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.os.persistableBundleOf

class MainActivity : AppCompatActivity() {
    private val requestforpermission:ActivityResultLauncher<Array<String>>
            =registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    )
    {
                permissions->
        permissions.entries.forEach {
            val permissionName =it.key//it checks what is the permission we requested
            //it is mutable map with mutable entry
            val isGranted =it.value //it holds value if the permission is granted or not
            if(isGranted)
            { //checking which permission was granted
                if(permissionName==Manifest.permission.ACCESS_FINE_LOCATION)
                {
                    Toast.makeText(this,
                        "permession is given for fine location",
                        Toast.LENGTH_SHORT).show()
                }
                else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION)
                {
                    Toast.makeText(this,
                        "permession is coarse location",
                        Toast.LENGTH_SHORT).show()
                }
                else//if that is not given than camera is granted
                {
                    Toast.makeText(this,
                    "camera permission given",Toast.LENGTH_SHORT).show()
                }
            }
            else //the permission is not granted
            {
                if(permissionName ==Manifest.permission.ACCESS_FINE_LOCATION)
                {
                    Toast.makeText(this,
                        "permission denied for fine given",
                        Toast.LENGTH_SHORT).show()
                }
                else if (permissionName ==Manifest.permission.ACCESS_COARSE_LOCATION)
                {
                    Toast.makeText(this,
                        "permession denied for coarse location",
                        Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this,
                        "permession denied for camera",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button_for_permission:Button=findViewById(R.id.ask_permission)
        button_for_permission.setOnClickListener {
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
            {
                dialogue("permission requires camera accesss as well as location","camera cannot be used because permission is denied")
            //it enters into the block if its true which means that the user has denies the
                //permission
            }
            else
            {
            requestforpermission.launch(
                arrayOf(Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )//it means user has accepted the permission
            }
        }
    }
    private fun dialogue(title:String , message:String)
    {
        val builder:AlertDialog.Builder =AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message).setPositiveButton("cancel")
        {
            dialog,_ -> dialog.dismiss()
        }
        builder.create().show()
    }
}