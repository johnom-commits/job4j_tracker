package ru.job4j.tracker;

import java.util.List;

public class FindAllAction extends BaseAction {

    protected FindAllAction(int key, String name) {
        super(key, name);
    }

    @Override
    public String name() {
        return "Show all items";
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        List<Item> copy = tracker.findAll();
        if (copy.size() == 0) {
            System.out.println("There are no items");
        } else {
            System.out.println("=== All items =====");
        }
        for (Item item : copy) {
            System.out.println("Id: " + item.getId() + ", name: " + item.getName());
        }
        return true;
    }
}
