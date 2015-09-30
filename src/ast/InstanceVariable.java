package ast;

public class InstanceVariable extends Variable {

    private boolean still;
    private boolean finale;

    public boolean isStill() {
        return still;
    }

    public void setStill(boolean still) {
        this.still = still;
    }

    public boolean isFinale() {
        return finale;
    }

    public void setFinale(boolean finale) {
        this.finale = finale;
    }

    public InstanceVariable( Type type, String name, boolean still, boolean finale ) {
        super(name, type);
        this.still = still;
        this.finale = finale;
    }



}