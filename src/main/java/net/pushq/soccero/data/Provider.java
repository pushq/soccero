package net.pushq.soccero.data;

import com.google.common.base.Joiner;
import net.pushq.soccero.domain.Game;
import net.pushq.soccero.domain.Player;
import net.pushq.soccero.domain.Register;
import net.pushq.soccero.domain.StatsRecord;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static net.pushq.soccero.convert.Mapper.mapToObject;

public class Provider {

    public static final String SEPARATOR = " ";
    private final JdbcTemplate jdbcTemplate;

    public Provider() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("192.168.1.50");
        dataSource.setDatabaseName("soccero");
        dataSource.setUser("postgres");
        dataSource.setPassword("");

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Player> players() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from player_view");

        return maps.
                stream().
                map(map -> mapToObject(map, new Player())).
                collect(Collectors.toList());
    }

    public List<Game> games() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from games_view");

        return map(maps);
    }

    public List<Game> activeGames() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from games_active_view");
        return map(maps);
    }

    public List<Game> currentMonthGames() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from games_current_month");
        return map(maps);
    }

    public List<Game> lastMonthGames() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from games_previous_month");
        return map(maps);
    }

    private List<Game> map(List<Map<String, Object>> maps) {
        return maps.
                stream().
                map(map -> mapToObject(map, new Game())).
                collect(Collectors.toList());
    }

    public List<StatsRecord> winStats() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from win_stats_view");

        return maps.
                stream().
                map(map -> mapToObject(map, new StatsRecord())).
                collect(Collectors.toList());
    }

    @Deprecated
    public void register(Register register) {
        jdbcTemplate.update("insert into game (red1, red2, blue1, blue2, \"redScore\", \"blueScore\", date) values (?,?,?,?,?,?,?)",
                register.getPlayerR1(),
                register.getPlayerR2(),
                register.getPlayerB1(),
                register.getPlayerB2(),
                register.getRedScore(),
                register.getBlueScore(),
                new Date());
    }

    public List<Game> activeGames7() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(
                Joiner.on(SEPARATOR).join(
                        "SELECT game.\"redScore\",",
                                "game.\"blueScore\",",
                                "game.date, r1.name AS \"playerR1\",",
                                "r2.name AS \"playerR2\",",
                                "b1.name AS \"playerB1\",",
                                "b2.name AS \"playerB2\"",
                        "FROM game7 as game, player r1, player r2, player b1, player b2",
                        "WHERE game.red1 = r1.id AND game.red2 = r2.id",
                                "AND game.blue1 = b1.id AND game.blue2 = b2.id",
                                "AND r1.active = true AND r2.active = true",
                                "AND b1.active = true AND b2.active = true",
                                "AND game.date > ('now'::text::date - '1 mon'::interval)",
                        "ORDER BY game.id;"
                )
        );
        return map(maps);
    }

    public void register7(Register register) {
        jdbcTemplate.update("insert into game7 (red1, red2, blue1, blue2, \"redScore\", \"blueScore\", date) values (?,?,?,?,?,?,?)",
                register.getPlayerR1(),
                register.getPlayerR2(),
                register.getPlayerB1(),
                register.getPlayerB2(),
                register.getRedScore(),
                register.getBlueScore(),
                new Date());
    }
}
