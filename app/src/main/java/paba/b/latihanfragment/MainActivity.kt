package paba.b.latihanfragment

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mFragmentManager = supportFragmentManager
        val mfSatu = fPageGame()

        mFragmentManager.findFragmentByTag(fPageGame::class.java.simpleName)
        mFragmentManager
            .beginTransaction()
            .add(R.id.frameContainer, mfSatu, fPageGame::class.java.simpleName)
            .commit()

//        nav
        val _tvHalaman = findViewById<TextView>(R.id.tvHalaman)

        val _btnFragment1 = findViewById<Button>(R.id.btnFragment1)
        _btnFragment1.setOnClickListener {
            _tvHalaman.text = "Halaman 1"

            val mfPageGame = fPageGame()
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfPageGame, fPageGame::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        val _btnFragment2 = findViewById<Button>(R.id.btnFragment2)
        _btnFragment2.setOnClickListener {
            _tvHalaman.text =  "Halaman 2"

            val mfPageScore = pageScore()
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfPageScore, pageScore::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        val _btnFragment3 = findViewById<Button>(R.id.btnFragment3)
        _btnFragment3.setOnClickListener {
            _tvHalaman.text =  "Halaman 3"

            val mfSetBoundary = fPageSetBoundary()
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mfSetBoundary, fPageSetBoundary::class.java.simpleName)
                addToBackStack(null)
                commit()
            }

        }
    }
}