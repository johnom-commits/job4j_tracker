package ru.job4j.tracker;

public class CreateAction extends BaseAction {
    @Override
    public String name() {
        return "Add a new item";
    }

    public CreateAction(int key, String name) {
        super(key, name);
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        tracker.add(item);
        return true;
    }
}
