package boardgame.model;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(Player.class)
public interface PlayerDao {

    @SqlUpdate("""
    CREATE TABLE IF NOT EXISTS playertable(
        name VARCHAR PRIMARY KEY,
        stepCount INTEGER,
        score INTEGER
    )
    """)
    void createPlayerTable();

    @SqlUpdate("""
    INSERT INTO playertable VALUES(:name, :stepCount, :score)
    """)
    void insertPlayer(@BindBean Player player);

    @SqlQuery("""
    SELECT * FROM playertable ORDER BY score DESC, stepCount
    """)
    List<Player> listPlayers();
}
