package com.instance.dataxbranch.data.local.views

/*
@DatabaseView(
    """
    SELECT quests.id
"""
)
 class QuestDBView(
    val id: String
){
    fun getAll(): Flow<List<QuestEntity>> {
        return com.instance.dataxbranch.data.daos.QuestDao.getAll()

    }
*/



/*
COUNT(*) as episodeCount, COUNT(ew.watched_at) as watchedEpisodeCount


    GROUP BY quests.id
    INNER JOIN seasons AS s ON fs.show_id = s.show_id
    INNER JOIN episodes AS eps ON eps.season_id = s.id
    LEFT JOIN episode_watch_entries as ew ON ew.episode_id = eps.id
WHERE eps.first_aired IS NOT NULL
        AND datetime(eps.first_aired) < datetime('now')

        AND s.ignored = 0
 */