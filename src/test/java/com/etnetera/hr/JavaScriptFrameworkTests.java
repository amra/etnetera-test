package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.FrameworkVersion;
import com.etnetera.hr.data.JavaScriptFramework;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkTests {

    private static final Logger LOG = LoggerFactory.getLogger(JavaScriptFrameworkTests.class);

    @Autowired
    private JavaScriptFrameworkController controller;

    @Autowired
    private MockMvc mockMvc;

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
//        controller.save(null);
        controller.show(null);
        controller.delete(null);
    }

    @Test
    public void save() throws Exception {
        mockMvc.perform(post("/framework/save/")
                .contentType("text/plain")
                .content("{" +
                        "\"name\":\"testUserDetails\"," +
                        "\"hypeLevel\":\"AWESOME\"," +
//                        "\"lastName\":\"xxx\"," +
//                        "\"password\":\"xxx\"" +
                        "}"
                )
        )
                .andExpect(status().is2xxSuccessful())
        ;
        assertNotNull(controller.show(1L));
    }

    @Test
    public void mockNull() throws Exception {
        mockMvc.perform(get("/framework/show/")
                .contentType("text/plain"))
                .andExpect(status().is4xxClientError())
//                .andExpect(content().string("foovalue"))
        ;
        mockMvc.perform(get("/framework/delete/")
                .contentType("text/plain"))
                .andExpect(status().is4xxClientError())
        ;
        mockMvc.perform(post("/framework/save/")
                .contentType("text/plain"))
                .andExpect(status().is4xxClientError())
        ;
        mockMvc.perform(post("/framework/update/")
                .contentType("text/plain"))
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    public void deleteMock() throws Exception {
        controller.save(build("test"));
        mockMvc.perform(get("/framework/delete/1")
                .contentType("text/plain"))
                .andExpect(status().is2xxSuccessful())
        ;
        assertFalse(controller.frameworks().iterator().hasNext());
    }

    private JavaScriptFramework build(String name) {
        return build(Arrays.array(name)).iterator().next();
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
