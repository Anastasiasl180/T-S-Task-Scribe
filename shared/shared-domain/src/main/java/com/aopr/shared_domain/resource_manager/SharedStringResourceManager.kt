package com.aopr.shared_domain.resource_manager

import com.aopr.shared_domain.R

sealed class SharedStringResourceManager(val messageId: Int) {
    data object DefaultMessage :
        SharedStringResourceManager(R.string.DefaultErrorMessage)
    data object EmptyTittleMessage :
        SharedStringResourceManager(R.string.EmptyTittle)
    data object EmptyDescriptionMessage :
        SharedStringResourceManager(R.string.EmptyDescription)
    data object EmptyDataForReminderMessage :
        SharedStringResourceManager(R.string.EmptyDateReminder)
    data object EmptyTimeForReminderMessage :
        SharedStringResourceManager(R.string.EmptyTimeReminder)
    data object EmptyDataForTaskToBeDoneMessage :
        SharedStringResourceManager(R.string.EmptyDateForTaskToBeDoneReminder)
    data object WrongTimeForReminderMessage :
        SharedStringResourceManager(R.string.WrongTimeSet)
}