package co.edu.acde.domain.enumeration;

/**
 * The State enumeration.
 */
public enum State {
    TRAINED("Capacitado"),
    NOTTRAINED("Nocapacitado");

    private final String value;

    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
