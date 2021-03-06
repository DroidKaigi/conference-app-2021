package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.Staff
import kotlinx.coroutines.flow.Flow

interface StaffRepository {
    fun staffContents(): Flow<List<Staff>>
}
