package io.github.droidkaigi.feeder

import android.content.Context
import androidx.startup.Initializer
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
class FlipperInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val hilt = EntryPointAccessors.fromApplication(context, HiltEntryPoint::class.java)
        SoLoader.init(context, false)
        AndroidFlipperClient.getInstance(context).apply {
            addPlugin(DatabasesFlipperPlugin(context))
            addPlugin(hilt.networkFlipperPlugin())
            addPlugin(SharedPreferencesFlipperPlugin(context))
            start()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface HiltEntryPoint {
        fun networkFlipperPlugin(): NetworkFlipperPlugin
    }
}
