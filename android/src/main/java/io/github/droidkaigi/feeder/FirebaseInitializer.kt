package io.github.droidkaigi.feeder

import android.content.Context
import androidx.startup.Initializer
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class FirebaseInitializer : Initializer<Firebase> {
    override fun create(context: Context): Firebase {
        Firebase.initialize(context)
        return Firebase
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

class ArborInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Arbor.sow(Seedling())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
