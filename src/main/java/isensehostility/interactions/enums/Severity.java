package isensehostility.interactions.enums;

public enum Severity {
    WEAK(0),
    MILD(1),
    SEVERE(2),
    AUTO(0);

    private int effectStrength;

    Severity(int effectStrength) {
        this.effectStrength = effectStrength;
    }

    public int getEffectStrength() {
        return effectStrength;
    }
}
