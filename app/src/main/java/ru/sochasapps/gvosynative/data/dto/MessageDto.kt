package ru.sochasapps.gvosynative.data.dto
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val conversationId: String,
    val role: MessageRole,
    val content: String,
    val audioUrl: String? = null,
    val status: MessageStatus,
    val noteId: Long? = null,
    val createdAt: String
)

enum class MessageRole {
    @SerializedName("USER")
    USER,

    @SerializedName("ASSISTANT")
    ASSISTANT
}

enum class MessageStatus {
    @SerializedName("PENDING")
    PENDING,

    @SerializedName("COMPLETED")
    COMPLETED,

    @SerializedName("ERROR")
    ERROR
}

data class AssistantContent(
    @SerializedName("classification")
    val classification: String? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("actions")
    val actions: List<String>? = null,

    @SerializedName("assistant_reply")
    val assistantReply: String? = null,

    @SerializedName("extra")
    val extra: ExtraData? = null,

    @SerializedName("other_hint")
    val otherHint: String? = null
)

@Serializable
data class ExtraData(
    @SerializedName("todo_list")
    val todoList: TodoListData? = null,

    @SerializedName("task")
    val task: TaskData? = null,

    @SerializedName("journal")
    val journal: JournalData? = null,

    @SerializedName("weekly_plan")
    val weeklyPlan: WeeklyPlanData? = null,

    @SerializedName("project_idea")
    val projectIdea: ProjectIdeaData? = null,

    @SerializedName("other")
    val other: String? = null
)

@Serializable
data class TodoListData(
    @SerializedName("items")
    val items: List<String>? = null
)

@Serializable
data class TaskData(
    @SerializedName("description")
    val description: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("priority")
    val priority: String? = null
)

@Serializable
data class WeeklyPlanData(
    @SerializedName("date")
    val date: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("activity")
    val activity: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("days")
    val days: List<DayPlanData>? = null
)

@Serializable
data class DayPlanData(
    @SerializedName("day")
    val day: String? = null,

    @SerializedName("tasks")
    val tasks: List<String>? = null
)

@Serializable
data class ProjectIdeaData(
    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("steps")
    val steps: List<String>? = null
)

@Serializable
data class JournalData(
    @SerializedName("entry")
    val entry: String? = null,

    @SerializedName("mood")
    val mood: String? = null,

    @SerializedName("date")
    val date: String? = null
)
