package io.github.droidkaigi.feeder.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.TimetableSpeaker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.datetime.Instant

interface TimetableItemDao {
    fun selectAll(): Flow<List<TimetableItem>>
    fun insert(items: List<TimetableItem>)
    fun deleteAll()
}

internal class TimetableItemDaoImpl(database: Database) : TimetableItemDao {
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

    override fun insert(items: List<TimetableItem>) {
        items.forEach { item ->
            when (item) {
                is TimetableItem.Session -> {
                    sessionQueries.insert(item)
                    item.speakers.forEach { speaker ->
                        speakerQueries.insert(
                            id = item.id,
                            speaker = speaker,
                        )
                    }
                }
                is TimetableItem.Special -> {
                    specialQueries.insert(item)
                    item.speakers.forEach { speaker ->
                        speakerQueries.insert(
                            id = item.id,
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

private fun TimetableItemSessionQueries.insert(session: TimetableItem.Session) {
    this.insert(
        timetableItemSession = TimetableItemSession(
            id = session.id,
            jaTitle = session.title.jaTitle,
            enTitle = session.title.enTitle,
            startsAt = session.startsAt.toEpochMilliseconds(),
            endsAt = session.endsAt.toEpochMilliseconds(),
        ),
    )
}

private fun TimetableItemSpecialQueries.insert(session: TimetableItem.Special) {
    this.insert(
        timetableItemSpecial = TimetableItemSpecial(
            id = session.id,
            jaTitle = session.title.jaTitle,
            enTitle = session.title.enTitle,
            startsAt = session.startsAt.toEpochMilliseconds(),
            endsAt = session.endsAt.toEpochMilliseconds(),
        ),
    )
}

private fun TimetableItemSpeakerQueries.insert(id: String, speaker: TimetableSpeaker) {
    this.insert(
        timetableItemSpeaker = TimetableItemSpeaker(
            timetablePrimaryId = id,
            name = speaker.name,
            iconUrl = speaker.iconUrl,
        ),
    )
}

private fun List<SelectAllSession>.toSessionItems(): List<TimetableItem.Session> {
    return this.foldRight(mapOf<String, TimetableItem.Session>()) { row, acc ->
        val feedItem = if (acc.containsKey(row.id)) {
            val oldFeedItem = acc.getValue(row.id)
            oldFeedItem.copy(
                speakers = oldFeedItem.speakers + TimetableSpeaker(
                    name = row.speakerName,
                    iconUrl = row.speakerIconUrl,
                ),
            )
        } else {
            TimetableItem.Session(
                id = row.id,
                title = MultiLangText(
                    jaTitle = row.jaTitle,
                    enTitle = row.enTitle,
                ),
                startsAt = Instant.fromEpochMilliseconds(row.startsAt),
                endsAt = Instant.fromEpochMilliseconds(row.endsAt),
                speakers = listOf(TimetableSpeaker(
                    name = row.speakerName,
                    iconUrl = row.speakerIconUrl,
                )),
            )
        }
        acc + mapOf(row.id to feedItem)
    }.values.toList()
}

private fun List<SelectAllSpecial>.toSpecialItems(): List<TimetableItem.Special> {
    return this.foldRight(mapOf<String, TimetableItem.Special>()) { row, acc ->
        val feedItem = if (acc.containsKey(row.id)) {
            val oldFeedItem = acc.getValue(row.id)
            oldFeedItem.copy(
                speakers = oldFeedItem.speakers + TimetableSpeaker(
                    name = row.speakerName,
                    iconUrl = row.speakerIconUrl,
                ),
            )
        } else {
            TimetableItem.Special(
                id = row.id,
                title = MultiLangText(
                    jaTitle = row.jaTitle,
                    enTitle = row.enTitle,
                ),
                startsAt = Instant.fromEpochMilliseconds(row.startsAt),
                endsAt = Instant.fromEpochMilliseconds(row.endsAt),
                speakers = listOf(TimetableSpeaker(
                    name = row.speakerName,
                    iconUrl = row.speakerIconUrl,
                )),
            )
        }
        acc + mapOf(row.id to feedItem)
    }.values.toList()
}
