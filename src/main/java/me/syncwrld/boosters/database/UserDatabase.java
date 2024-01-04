package me.syncwrld.boosters.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import me.syncwrld.boosters.BoosterEngine;
import me.syncwrld.boosters.database.connector.DatabaseConnector;
import me.syncwrld.boosters.model.PlayerModel;

public class UserDatabase implements IdentifiableRepository, DatabaseHelper {

    private final Connection connection;
    private final String connectionType;

    public UserDatabase(DatabaseConnector connector) {
        this.connection = connector.getConnection();
        this.connectionType = connector.getDatabaseType();

        if (connection != null)
            createTables();
    }

    public static UserDatabase of(BoosterEngine engine) {
        return new UserDatabase(engine.getDatabaseConnector());
    }

    @Override
    public String getName() {
        return "users";
    }

    @Override
    public boolean hasMoreThanOneTable() {
        return false;
    }

    @Override
    public void createTables() {
        final String query = "create table if not exists syncboosters_users (user varchar(16) PRIMARY KEY, boosters TEXT)";
        try {
            this.prepare(connection, query).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(PlayerModel playerModel) {
        String name = playerModel.getBukkitPlayer().getName();
        String modelJson = playerModel.getAsJson();

        String query = (connectionType.equalsIgnoreCase("mysql") ? "insert or replace into " : "insert into ") + "$table_0 (user, boosters) VALUES (?, ?)";
        query = consolidate(query);

        try {
            PreparedStatement preparedStatement = prepare(connection, query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, modelJson);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerModel get(String name) {
        String query = consolidate("select * from $table_0 where user = ?");
        try {
            PreparedStatement preparedStatement = prepare(connection, query);
            preparedStatement.setString(1, name);

            async(() -> {
                try {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        String json = resultSet.getString("boosters");
                        return PlayerModel.fromJson(json);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Map<String, Integer> getTableIDs() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("users", 0);
        return map;
    }

}
