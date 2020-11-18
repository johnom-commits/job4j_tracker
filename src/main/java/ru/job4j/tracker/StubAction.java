package ru.job4j.tracker;

public class StubAction extends BaseAction {
    private boolean call = false;

    protected StubAction(int key, String name) {
        super(key, name);
    }

    @Override
    public String name() {
        return "Stub action";
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        call = true;
        return false;
    }

    public boolean isCall() {
        return call;
    }
}