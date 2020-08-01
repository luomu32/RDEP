package xyz.luomu32.rdep.project.pojo;

import lombok.Getter;
import lombok.Setter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import xyz.luomu32.rdep.common.exception.ServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CheckstyleResult {

    private int waringCount = 0;
    private int errorCount = 0;
    private List<Code> codes = new ArrayList<>();

    public static CheckstyleResult buildFromXml(File xml) {
        if (!xml.exists())
            throw new IllegalStateException("checkstyle.result.file.not.exist");

        CheckstyleResult result = new CheckstyleResult();

        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(xml);
        } catch (DocumentException e) {
            throw new ServiceException("parse.checkstyle.result.file.fail", e.getMessage());
        }
        int totalWaringCount = 0, totalErrorCount = 0;
        List<Code> codes = new ArrayList<>();
        for (Element file : doc.getRootElement().elements("file")) {
            List<Element> errors = file.elements("error");
            if (errors.isEmpty())
                continue;
            String name = file.attribute("name").getValue();
            Code code = parseCode(name);
            int waringCount = 0, errorCount = 0;
            List<Message> messages = new ArrayList<>();
            for (Element error : errors) {
                String type = error.attribute("severity").getValue();
                if (type.equals("warning")) {
                    waringCount++;
                    totalWaringCount++;
                } else if (type.equals("error")) {
                    errorCount++;
                    totalErrorCount++;
                }
                Message message = new Message();
                message.setLine(Integer.parseInt(error.attribute("line").getValue()));
                message.setType(type);
                message.setMessage(error.attributeValue("message"));
                message.setCheckName(error.attributeValue("source"));
                messages.add(message);
            }
            code.setWaringCount(waringCount);
            code.setErrorCount(errorCount);
            code.setMessages(messages);

            codes.add(code);
        }
        result.setCodes(codes);
        result.setWaringCount(totalWaringCount);
        result.setErrorCount(totalErrorCount);
        return result;
    }

    private static Code parseCode(String name) {
        int pos = name.indexOf("java") + 5;
        name = name.substring(pos);
        pos = name.lastIndexOf('/');
        String shortName = name.substring(pos + 1);
        String packageName = name.substring(0, pos).replaceAll("/", ".");
        Code code = new Code();
        code.setName(shortName);
        code.setPackageName(packageName);
        return code;
    }

    @Getter
    @Setter
    public static class Code {
        private String packageName;
        private String name;
        private int waringCount = 0;
        private int errorCount = 0;
        private List<Message> messages;

        public String getFullName() {
            return this.packageName + '.' + this.name;
        }
    }

    @Getter
    @Setter
    public static class Message {
        private int line;
        private String type;
        private String message;
        private String checkName;
    }
}
