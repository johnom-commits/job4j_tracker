package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;

public class StartUISQL extends StartUI {

    public StartUISQL(Input input, ITracker tracker, Consumer<String> output) {
        super(input, tracker, output);
    }

    public static Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            var config = new Properties();
            config.load(Objects.requireNonNull(in));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        try (ITracker tracker = new HbmTracker()) {
//            try (ITracker tracker = new TrackerSQL(init())) {
            List<UserAction> actions = new ArrayList<>();
            actions.add(new CreateAction(0, "Add a new item"));
            actions.add(new FindAllAction(1, "Show all items"));
            actions.add(new ReplaceAction(2, "Edit item"));
            actions.add(new DeleteAction(3, "Delete item"));
            actions.add(new FindByIdAction(4, "Find item by Id"));
            actions.add(new FindByNameAction(5, "Find items by name"));
            actions.add(new ExitAction(6, "Exit program"));
            new StartUISQL(validate, tracker, System.out::println).init(actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
