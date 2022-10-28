package com.podzirei.onlineshop.service;

import com.study.ioc.context.ApplicationContext;
import com.study.ioc.context.impl.GenericApplicationContext;
import com.study.ioc.reader.sax.XmlBeanDefinitionReader;

public class ServiceLocator {

    private static final ApplicationContext APPLICATIONCONTEXT;

    static{
        String[] paths = {"context.xml"};
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(paths);

        try {
            APPLICATIONCONTEXT = new GenericApplicationContext(xmlBeanDefinitionReader);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getBean(String beanName){
        return APPLICATIONCONTEXT.getBean(beanName);
    }
}
