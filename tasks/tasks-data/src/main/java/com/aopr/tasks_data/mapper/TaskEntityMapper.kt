package com.aopr.tasks_data.mapper

import com.aopr.tasks_data.room.TaskEntity
import com.aopr.tasks_domain.models.Task

internal fun Task.mapToEntity():TaskEntity{
    return TaskEntity(
        id = this.id,
        tittle = this.tittle,
        description = this.description,
        dateToBeDone = this.dateOfTaskToBeDone,
        time = this.timeForReminder,
        isCompleted = this.isCompleted,
        importance = this.importance,
        listOfSubtasks = this.listOfSubtasks,
        dateForReminder = this.dateForReminder,
        uuid = this.uuid
    )
}
internal fun TaskEntity.mapToTask():Task{
    return Task(
        id = this.id,
        tittle = this.tittle,
        description = this.description,
        dateForReminder = this.dateForReminder,
        timeForReminder = this.time,
        isCompleted = this.isCompleted,
        importance = this.importance,
        listOfSubtasks = this.listOfSubtasks,
        uuid = this.uuid,
        dateOfTaskToBeDone = this.dateToBeDone
    )
}
