package com.lounah.poster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lounah.core.widget.bottomnav.BottomNavigationMenuItem
import com.lounah.core.widget.bottomnav.PosterBottomNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavMain.addMenuItems(listOf(
                PosterBottomNavigationBar.MenuItem(1, "Feed", R.drawable.ic_menu_feed, R.color.black, R.color.grayLight),
                PosterBottomNavigationBar.MenuItem(2, "Notifications", R.drawable.ic_menu_notifications, R.color.black, R.color.grayLight),
                PosterBottomNavigationBar.MenuItem(3, "Search", R.drawable.ic_menu_explore, R.color.black, R.color.grayLight)
        ))

        bottomNavMain.selectItemWithId(1)
    }
}
