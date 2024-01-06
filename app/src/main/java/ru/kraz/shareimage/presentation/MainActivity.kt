package ru.kraz.shareimage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.kraz.shareimage.R
import ru.kraz.shareimage.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.drawing -> launchFragment(DrawingFragment.newInstance())
                else -> launchFragment(ImagesFragment.newInstance())
            }
            true
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onStop() {
        super.onStop()
        val cacheFiles = cacheDir.listFiles()
        if (cacheFiles != null) {
            for (file in cacheFiles) {
                file.delete()
            }
        }
    }
}