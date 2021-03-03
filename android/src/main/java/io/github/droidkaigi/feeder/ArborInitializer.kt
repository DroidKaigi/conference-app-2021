package io.github.droidkaigi.feeder

import android.content.Context
import androidx.startup.Initializer
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling

class ArborInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Arbor.sow(Seedling())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
