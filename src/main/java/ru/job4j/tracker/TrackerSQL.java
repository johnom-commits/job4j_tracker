package ru.job4j.tracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackerSQL implements ITracker, AutoCloseable {
    private Connection connection;

    public TrackerSQL(Connection con) {
        connection = con;
        initTablesDB();
    }

    private void initTablesDB() {
        try (final Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS items (id serial PRIMARY KEY, name varchar(50) NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item add(Item item) {
        try (final PreparedStatement st = connection.prepareStatement("INSERT INTO items (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.executeUpdate();
            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    int keyValue = key.getInt(1);
                    item.setId(keyValue);
                    return item;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Could not create new item");
    }

    @Override
    public boolean replace(Integer id, Item item) {
        int result = 0;
        try (final PreparedStatement st = connection.prepareStatement("UPDATE items SET name = ? WHERE id = ?")) {
            st.setString(1, item.getName());
            st.setInt(2, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    @Override
    public boolean delete(Integer id) {
        int result = 0;
        try (final PreparedStatement st = connection.prepareStatement("DELETE FROM items WHERE id = ?")) {
            st.setInt(1, id);
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result == 1;
    }

    @Override
    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        try (final Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM items");
            int id;
            String name = "";
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                Item item = new Item(id, name);
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> list = new ArrayList<>();
        try (final PreparedStatement st = connection.prepareStatement("SELECT * FROM items WHERE name = ?")) {
            st.setString(1, key);
            ResultSet rs = st.executeQuery();
            int id;
            while (rs.next()) {
                id = rs.getInt(1);
                Item item = new Item(id, key);
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Item findById(Integer id) {
        Item item = null;
        try (final PreparedStatement st = connection.prepareStatement("SELECT * FROM items WHERE id = ?")) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String name = rs.getString(1);
                item = new Item(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }
}
