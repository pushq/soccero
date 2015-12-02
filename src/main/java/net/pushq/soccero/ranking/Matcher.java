package net.pushq.soccero.ranking;

import com.google.common.collect.Lists;
import net.pushq.soccero.domain.StatsRecord;
import net.pushq.soccero.domain.TeamMatch;

import java.util.Collections;
import java.util.List;

/**
 * Created by Michal on 2015-12-02.
 */
public class Matcher {

    public List<TeamMatch> match(List<StatsRecord> statsRecords) {

        List<TeamMatch> teamMatches = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {
            Collections.shuffle(statsRecords);

            List<StatsRecord> matchCandidate = statsRecords.subList(0, 4);
            matchCandidate.sort((m1, m2) -> m2.getRatio().compareTo(m1.getRatio()));

            StatsRecord best = matchCandidate.get(0);
            StatsRecord mid1 = matchCandidate.get(1);
            StatsRecord mid2 = matchCandidate.get(2);
            StatsRecord worst = matchCandidate.get(3);

            teamMatches.add(new TeamMatch(
                    best.getName(),
                    worst.getName(),
                    mid1.getName(),
                    mid2.getName(),
                    best.getRatio().add(worst.getRatio()).subtract(mid1.getRatio()).subtract(mid2.getRatio()))
            );
        }

        teamMatches.sort((m1, m2) -> m1.match.abs().compareTo(m2.match.abs()));

        return teamMatches.subList(0, 15);
    }

}
