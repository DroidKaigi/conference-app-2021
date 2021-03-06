package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.repository.StaffRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class StaffRepositoryImpl(
    private val staffApi: StaffApi,
) : StaffRepository {
    override fun staffContents(): Flow<List<Staff>> {
        return flow {
            emit(staffApi.fetch())
        }
    }
}
