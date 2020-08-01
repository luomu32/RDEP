package xyz.luomu32.rdep.user.test;

import org.junit.Test;
import xyz.luomu32.rdep.user.service.impl.PasswordGenerator;

public class PasswordGeneratorTest {

    @Test
    public void test() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generate(8, 2);
        assert password.length() == 8;
    }
}
