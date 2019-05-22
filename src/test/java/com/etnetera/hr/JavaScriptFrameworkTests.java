package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sun.security.auth.UserPrincipal;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        assertEquals(0, result.get(0).getDeprecationDate().compareTo(Date.valueOf("2019-11-15")));
        assertEquals("Angular", result.get(1).getName());
        assertEquals(0, result.get(1).getDeprecationDate().compareTo(Date.valueOf("2019-11-15")));
    }

    @Test
    public void find() throws Exception {
        build(new String[]{"React"}).forEach(f -> controller.save(f));
        mockMvc.perform(get("/framework/list")
            .contentType("text/plain"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json("[{\"id\":1,\"name\":\"React\",\"hypeLevel\":\"NORMAL\",\"version\":\"1.0\",\"deprecationDate\":\"2019-11-15\"}]"))
        ;
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
    public void save() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SimpleBeanPropertyFilter idFilter = SimpleBeanPropertyFilter.serializeAllExcept("id");
        FilterProvider filters = new SimpleFilterProvider().addFilter("id", idFilter);
        ObjectWriter ow = mapper.writer(filters).withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(build("tester"));
        LOG.info("requestJson:\n{}", requestJson);

        // Saving new instance succeed.
        mockMvc.perform(post("/framework/save/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is2xxSuccessful());
        assertNotNull(controller.show(1L));

        // Saving the same object fails.
        mockMvc.perform(post("/framework/save/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username="user",roles={"USER"})
    public void update() throws Exception {
        controller.save(build("tester"));
        JavaScriptFramework framework = controller.show(1L).get();
        framework.setVersion("2.0");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(framework);
        LOG.info("requestJson:\n{}", requestJson);

        // Updating the instance.
        mockMvc.perform(
                post("/framework/save/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)
//                    .principal(new UserPrincipal("user"))
                )
                .andExpect(status().is2xxSuccessful());
        framework = controller.show(1L).get();
        assertEquals("2.0", framework.getVersion());

    }

    @Test
    public void mockNull() throws Exception {
        mockMvc.perform(get("/framework/show/")
                .contentType("text/plain"))
                .andExpect(status().is4xxClientError())
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
            framework.setDeprecationDate(Date.valueOf("2019-11-15"));
            framework.setVersion("1.0");
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
