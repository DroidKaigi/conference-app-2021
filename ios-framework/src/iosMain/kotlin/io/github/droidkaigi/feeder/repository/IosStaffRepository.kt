package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.NonNullFlowWrapper
import io.github.droidkaigi.feeder.Staff

interface IosStaffRepository {
    fun staffContents(): NonNullFlowWrapper<List<Staff>>
}

class IosStaffRepositoryImpl(
    private val staffRepository: StaffRepository
) : IosStaffRepository {
    override fun staffContents(): NonNullFlowWrapper<List<Staff>> {
        return NonNullFlowWrapper(
            staffRepository.staffContents()
        )
    }
}
