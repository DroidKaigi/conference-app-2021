package io.github.droidkaigi.feeder.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.TimetableAsset
import io.github.droidkaigi.feeder.TimetableCategory
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.TimetableItemId
import io.github.droidkaigi.feeder.TimetableSpeaker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.datetime.Instant

interface TimetableItemDao {
    fun selectAll(): Flow<List<TimetableItem>>
    fun replace(items: List<TimetableItem>)
    fun insert(items: List<TimetableItem>)
    fun deleteAll()
}

internal class TimetableItemDaoImpl(private val database: Database) : TimetableItemDao {
    private val sessionQueries: TimetableItemSessionQueries = database.timetableItemSessionQueries
    private val specialQueries: TimetableItemSpecialQueries = database.timetableItemSpecialQueries
    private val speakerQueries: TimetableItemSpeakerQueries = database.timetableItemSpeakerQueries

    override fun selectAll(): Flow<List<TimetableItem>> {
        val allSession =
            sessionQueries.selectAllSession().asFlow().mapToList().map { it.toSessionItems() }
        val allSpecial =
            specialQueries.selectAllSpecial().asFlow().mapToList().map { it.toSpecialItems() }

        return allSession.zip(allSpecial) { sessions, specials -> sessions + specials }
    }

    override fun replace(items: List<TimetableItem>) {
        database.transaction {
            deleteAll()
            insert(items = items)
        }
    }

    override fun insert(items: List<TimetableItem>) {
        items.forEach { item ->
            when (item) {
                is TimetableItem.Session -> {
                    sessionQueries.insert(item)
                    item.speakers.forEach { speaker ->
                        speakerQueries.insert(
                            id = item.id.value,
                            speaker = speaker,
                        )
                    }
                }
                is TimetableItem.Special -> {
                    specialQueries.insert(item)
                    item.speakers.forEach { speaker ->
                        speakerQueries.insert(
                            id = item.id.value,
                            speaker = speaker,
                        )
                    }
                }
            }
        }
    }

    override fun deleteAll() {
        sessionQueries.deleteAll()
        specialQueries.deleteAll()
        speakerQueries.deleteAll()
    }
}

private const val stringListDivider = ","

private fun TimetableItemSessionQueries.insert(session: TimetableItem.Session) {
    this.insert(
        timetableItemSession = TimetableItemSession(
            id = session.id.value,
            jaTitle = session.title.jaTitle,
            enTitle = session.title.enTitle,
            startsAt = session.startsAt.toEpochMilliseconds(),
            endsAt = session.endsAt.toEpochMilliseconds(),
            idCategory = session.category.id.toLong(),
            jaCategory = session.category.title.jaTitle,
            enCategory = session.category.title.enTitle,
            targetAudience = session.targetAudience,
            language = session.language,
            assetSlideUrl = session.asset.slideUrl,
            assetVideoUrl = session.asset.videoUrl,
            levels = session.levels.joinToString(stringListDivider),
            description = session.description,
            jaMessage = session.message?.jaTitle,
            enMessage = session.message?.enTitle,
        ),
    )
}

private fun TimetableItemSpecialQueries.insert(session: TimetableItem.Special) {
    this.insert(
        timetableItemSpecial = TimetableItemSpecial(
            id = session.id.value,
            jaTitle = session.title.jaTitle,
            enTitle = session.title.enTitle,
            startsAt = session.startsAt.toEpochMilliseconds(),
            endsAt = session.endsAt.toEpochMilliseconds(),
            idCategory = session.category.id.toLong(),
            jaCategory = session.category.title.jaTitle,
            enCategory = session.category.title.enTitle,
            targetAudience = session.targetAudience,
            language = session.language,
            assetSlideUrl = session.asset.slideUrl,
            assetVideoUrl = session.asset.videoUrl,
            levels = session.levels.joinToString(stringListDivider),
        ),
    )
}

private fun TimetableItemSpeakerQueries.insert(id: String, speaker: TimetableSpeaker) {
    this.insert(
        timetableItemSpeaker = TimetableItemSpeaker(
            timetablePrimaryId = id,
            name = speaker.name,
            iconUrl = speaker.iconUrl,
            bio = speaker.bio,
            tagLine = speaker.tagLine,
        ),
    )
}

private fun List<SelectAllSession>.toSessionItems(): List<TimetableItem.Session> {
    return this.foldRight(mapOf<String, TimetableItem.Session>()) { row, acc ->
        val timetableItem = if (acc.containsKey(row.id)) {
            val oldTimetableItem = acc.getValue(row.id)
            oldTimetableItem.copy(
                speakers = oldTimetableItem.speakers + TimetableSpeaker(
                    name = row.speakerName,
                    bio = row.speakerBio,
                    iconUrl = row.speakerIconUrl,
                    tagLine = row.speakerTagLine,
                ),
            )
        } else {
            TimetableItem.Session(
                id = TimetableItemId(row.id),
                title = MultiLangText(
                    jaTitle = row.jaTitle,
                    enTitle = row.enTitle,
                ),
                startsAt = Instant.fromEpochMilliseconds(row.startsAt),
                endsAt = Instant.fromEpochMilliseconds(row.endsAt),
                category = TimetableCategory(
                    id = row.idCategory.toInt(),
                    title = MultiLangText(row.jaCategory, row.enCategory),
                ),
                targetAudience = row.targetAudience,
                language = row.language,
                asset = TimetableAsset(
                    slideUrl = row.assetSlideUrl,
                    videoUrl = row.assetVideoUrl,
                ),
                levels = row.levels.split(stringListDivider),
                description = row.description,
                speakers = listOf(
                    TimetableSpeaker(
                        name = row.speakerName,
                        bio = row.speakerBio,
                        iconUrl = row.speakerIconUrl,
                        tagLine = row.speakerTagLine,
                    ),
                ),
                message = if (row.jaMessage != null && row.enMessage != null) {
                    MultiLangText(row.jaMessage, row.enMessage)
                } else {
                    null
                },
            )
        }
        acc + mapOf(row.id to timetableItem)
    }.values.toList()
}

private fun List<SelectAllSpecial>.toSpecialItems(): List<TimetableItem.Special> {
    return this.foldRight(mapOf<String, TimetableItem.Special>()) { row, acc ->
        val timetableItem = if (acc.containsKey(row.id)) {
            val oldTimetableItem = acc.getValue(row.id)
            oldTimetableItem.copy(
                speakers = oldTimetableItem.speakers + TimetableSpeaker(
                    name = row.speakerName,
                    bio = row.speakerBio,
                    iconUrl = row.speakerIconUrl,
                    tagLine = row.speakerTagLine,
                ),
            )
        } else {
            TimetableItem.Special(
                id = TimetableItemId(row.id),
                title = MultiLangText(
                    jaTitle = row.jaTitle,
                    enTitle = row.enTitle,
                ),
                startsAt = Instant.fromEpochMilliseconds(row.startsAt),
                endsAt = Instant.fromEpochMilliseconds(row.endsAt),
                category = TimetableCategory(
                    id = row.idCategory.toInt(),
                    title = MultiLangText(row.jaCategory, row.enCategory),
                ),
                targetAudience = row.targetAudience,
                language = row.language,
                asset = TimetableAsset(
                    slideUrl = row.assetSlideUrl,
                    videoUrl = row.assetVideoUrl,
                ),
                levels = row.levels.split(stringListDivider),
                speakers = listOf(
                    TimetableSpeaker(
                        name = row.speakerName,
                        bio = row.speakerBio,
                        iconUrl = row.speakerIconUrl,
                        tagLine = row.speakerTagLine,
                    ),
                ),
            )
        }
        acc + mapOf(row.id to timetableItem)
    }.values.toList()
}

fun fakeTimetableItemDao(error: AppError? = null): TimetableItemDao = object : TimetableItemDao {
    private val channel = Channel<List<TimetableItem>>(Channel.CONFLATED).apply {
        trySend(emptyList())
    }

    override fun selectAll(): Flow<List<TimetableItem>> = flow {
        try {
            if (error != null) {
                throw error
            }
            for (item in channel) {
                emit(item)
            }
        } finally {
            channel.close()
        }
    }

    override fun replace(items: List<TimetableItem>) {
        channel.offer((channel.poll() ?: emptyList()) + items)
    }

    override fun insert(items: List<TimetableItem>) {
        channel.offer((channel.poll() ?: emptyList()) + items)
    }

    override fun deleteAll() {
        channel.offer(emptyList())
    }
}
