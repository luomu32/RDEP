//package xyz.luomu32.rdep.project.test;
//
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.junit.Test;
//
//import java.io.File;
//import java.util.Iterator;
//
//public class Dom4jTest {
//
//    @Test
//    public void test() throws DocumentException {
//        SAXReader reader = new SAXReader();
//
//        Document document = reader.read(new File("/Users/luomu32/IdeaProjects/spring-cloud-demo/pom.xml"));
//
////        String s = "//dependencies/dependency";
////        List<Node> nodes = document.getRootElement().selectNodes(s);
////        System.out.println(nodes.size());
//        for (Iterator<Element> it = document.getRootElement().elementIterator("dependencies"); it.hasNext(); ) {
//            Element element = it.next();
//            Iterator<Element> dep = element.elementIterator("dependency");
//
//            while (dep.hasNext()) {
//                Element d = dep.next();
//                System.out.println(d.element("groupId").getText());
//            }
//        }
//    }
//}
