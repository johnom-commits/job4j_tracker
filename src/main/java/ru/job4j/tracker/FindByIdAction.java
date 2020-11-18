package ru.job4j.tracker;

public class FindByIdAction extends BaseAction {

    protected FindByIdAction(int key, String name) {
        super(key, name);
    }

    @Override
    public String name() {
        return "Find item by Id";
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        Integer id = input.askInt("Please, enter Id:", 7);
        Item item = tracker.findById(id);
        if (item != null) {
            System.out.println("Item: " + item.getName());
        } else {
            System.out.println("Item does not find.");
        }
        return true;
    }
}
