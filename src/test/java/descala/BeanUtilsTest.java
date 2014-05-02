package descala;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BeanUtilsTest {

    @Test
    public void Capitalize() {
        assertEquals("FirstName", BeanUtils.capitalize("firstName"));
    }

    @Test
    public void Uncapitalize() {
        assertEquals("firstName", BeanUtils.uncapitalize("FirstName"));
    }
}
