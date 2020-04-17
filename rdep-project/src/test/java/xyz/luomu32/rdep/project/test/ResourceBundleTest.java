package xyz.luomu32.rdep.project.test;

import org.junit.Test;

import java.util.ResourceBundle;

public class ResourceBundleTest {

    @Test
    public void testGetBundle() {
        ResourceBundle bundle = ResourceBundle.getBundle("common-messages/message");
        bundle.keySet().forEach(System.out::println);
    }
}
