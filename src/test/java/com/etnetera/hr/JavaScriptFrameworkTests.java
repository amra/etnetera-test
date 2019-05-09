package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.FrameworkVersion;
import com.etnetera.hr.data.JavaScriptFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaScriptFrameworkTests {

    private static final Logger LOG = LoggerFactory.getLogger(JavaScriptFrameworkTests.class);

    @Autowired
    private JavaScriptFrameworkController controller;

    @Test
    public void saveAndFind() {
        build(new String[]{"React", "Angular"}).forEach(f -> controller.save(f));
        List<JavaScriptFramework> result = iterate(controller.frameworks());
        assertEquals(2, result.size());
        assertEquals("React", result.get(0).getName());
        assertEquals(4, result.get(0).getVersions().size());
        assertEquals("Angular", result.get(1).getName());
        assertEquals(4, result.get(1).getVersions().size());
    }

    @Test
    public void delete() {
        // Create.
        build(new String[]{"React", "Angular"}).forEach(f -> controller.save(f));
        List<JavaScriptFramework> result = iterate(controller.frameworks());
        assertEquals(2, result.size());

        // Delete.
        Long id = null;
        for (JavaScriptFramework framework : controller.frameworks()) {
            if (framework.getName().equals("React")) {
                id = framework.getId();
            }
        }
        controller.delete(id);
        result = iterate(controller.frameworks());

        assertEquals(1, result.size());
        assertEquals("Angular", result.get(0).getName());
    }

    @Test
    public void workWithNull() {
        controller.save(null);
        controller.show(null);
        controller.delete(null);
    }

    private Collection<JavaScriptFramework> build(String[] names) {
        List<JavaScriptFramework> result = new ArrayList<>();
        for (String name : names) {
            JavaScriptFramework framework = new JavaScriptFramework();
            framework.setName(name);
            framework.setHypeLevel(JavaScriptFramework.HypeLevel.NORMAL);
            List<FrameworkVersion> versions = new ArrayList<>();
            for (int i = 1; i < 5; i++) {
                FrameworkVersion version = new FrameworkVersion();
                version.setVersion(Integer.toString(i));
                if (i % 2 == 0) {
                    Date date = Date.valueOf("20"+(19 + i)+"-11-15");
                    version.setDeprecationDate(date);
                }
                versions.add(version);
            }
            framework.setVersions(versions);
            result.add(framework);
        }
        return result;
    }

    private List<JavaScriptFramework> iterate(Iterable<JavaScriptFramework> frameworks) {
        List<JavaScriptFramework> result = new ArrayList<>();
        for (JavaScriptFramework framework : frameworks) {
            result.add(framework);
        }
        return result;
    }
}
