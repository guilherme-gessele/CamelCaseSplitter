import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void camel_case_starting_with_lowercase() {
        var response = CamelCaseSplitter.converterCamelCase("nomeComposto");
        assertEquals(List.of("nome", "composto"), response);
    }

    @Test
    void camel_case_starting_with_uppercase() {
        var response = CamelCaseSplitter.converterCamelCase("NomeComposto");
        assertEquals(List.of("nome", "composto"), response);
    }

    @Test
    void uppercase() {
        var response = CamelCaseSplitter.converterCamelCase("CPF");
        assertEquals(List.of("CPF"), response);
    }

    @Test
    void camel_case_with_multiple_uppercase_letters() {
        var response = CamelCaseSplitter.converterCamelCase("numeroCPF");
        assertEquals(List.of("numero", "CPF"), response);
    }

    @Test
    void camel_case_with_uppercase_acronyms_and_words() {
        var response = CamelCaseSplitter.converterCamelCase("numeroCPFContribuinte");
        assertEquals(List.of("numero", "CPF", "contribuinte"), response);
    }

    @Test
    void camel_case_with_digits() {
        var response = CamelCaseSplitter.converterCamelCase("recupera10Primeiros");
        assertEquals(List.of("recupera", "10", "primeiros"), response);
    }

    @Test
    void starting_with_numbers() {
        assertThrows(IllegalArgumentException.class, () -> CamelCaseSplitter.converterCamelCase("10Primeiros"));
    }

    @Test
    void with_invalid_chars() {
        assertThrows(IllegalArgumentException.class, () -> CamelCaseSplitter.converterCamelCase("nome#Composto"));
    }
}
