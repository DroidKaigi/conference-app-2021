package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerStaffRepositoryImpl @Inject constructor(
    staffApi: StaffApi,
) : StaffRepositoryImpl(staffApi)
