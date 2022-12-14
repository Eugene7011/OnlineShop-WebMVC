package com.podzirei.onlineshop.web.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "templates/pages";
    private static final PageGenerator INSTANCE = new PageGenerator();
    private final Configuration configuration;

    public static PageGenerator getInstance() {
        return INSTANCE;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    private PageGenerator() {
        configuration = new Configuration();
    }

    public String getPage(String filename) {
        return getPage(filename, Collections.emptyMap());
    }
}
