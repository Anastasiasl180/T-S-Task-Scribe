package com.aopr.tasks_data.mapper

import com.aopr.tasks_data.room.TaskEntity
import com.aopr.tasks_domain.models.Task

internal fun Task.mapToEntity():TaskEntity{
    return TaskEntity(
        id = this.id,
        tittle = this.tittle,
        description = this.description,
        date = this.date,
        time = this.time,
        isCompleted = this.isCompleted,
        importance = this.importance,
        listOfSubtasks = this.listOfSubtasks
    )
}
