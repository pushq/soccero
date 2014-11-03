package net.pushq.soccero;

import net.pushq.soccero.domain.Game;
import net.pushq.soccero.domain.Player;
import net.pushq.soccero.domain.Register;
import net.pushq.soccero.domain.StatsRecord;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static net.pushq.soccero.convert.Mapper.mapToObject;

/**
 * Created by Michal on 2014-10-10.
 */
public class Provider {

    private final JdbcTemplate jdbcTemplate;

    public Provider() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("192.168.2.50");
        dataSource.setDatabaseName("soccero");
        dataSource.setUser("postgres");
        dataSource.setPassword("");

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Player> players() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from player");

        List<Player> returnList = maps.
                stream().
                map(map -> mapToObject(map, new Player())).
                collect(Collectors.toList());

        return returnList;
    }

    public List<Game> games() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from games_view");

        List<Game> returnList = maps.
                stream().
                map(map -> mapToObject(map, new Game())).
                collect(Collectors.toList());

        return returnList;
    }

    public List<Game> activeGames() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from games_active_view");

        List<Game> returnList = maps.
                stream().
                map(map -> mapToObject(map, new Game())).
                collect(Collectors.toList());

        return returnList;
    }

    public List<StatsRecord> winStats() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from win_stats_view");

        List<StatsRecord> returnList = maps.
                stream().
                map(map -> mapToObject(map, new StatsRecord())).
                collect(Collectors.toList());

        return returnList;
    }

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
}
