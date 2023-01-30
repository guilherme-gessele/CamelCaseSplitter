import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CamelCaseSplitterTest {

    @Test
    void lowercase() {
        var response = CamelCaseSplitter.converterCamelCase("nome");
        assertEquals(List.of("nome"), response);
    }

    @Test
    void starting_with_uppercase() {
        var response = CamelCaseSplitter.converterCamelCase("Nome");
        assertEquals(List.of("nome"), response);
    }
}
