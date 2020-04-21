package xyz.luomu32.rdep.project.test.web;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import xyz.luomu32.rdep.project.controller.TaskController;
import xyz.luomu32.rdep.project.model.Task;
import xyz.luomu32.rdep.project.pojo.task.TaskQueryRequest;
import xyz.luomu32.rdep.project.pojo.task.TaskResponse;
import xyz.luomu32.rdep.project.service.TaskService;
import xyz.luomu32.rdep.project.test.JunitCategory;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
@Category(JunitCategory.Dev.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private TaskService taskService;

    @Test
    public void testFetchAll() throws Exception {

        given(this.taskService.fetch(any(TaskQueryRequest.class)))
                .willReturn(Collections.singletonList(new TaskResponse()));

        this.mvc.perform(get("/projects/1/tasks?all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":null,\"title\":null,\"projectId\":null,\"moduleId\":null,\"moduleName\":null,\"start\":null,\"end\":null,\"chargerId\":null,\"chargerName\":null,\"state\":null,\"progress\":null,\"priority\":null}]"));
    }

    @Test
    public void testFetchPage() throws Exception {
        Page<TaskResponse> tasks = new PageImpl<TaskResponse>(Collections.singletonList(new TaskResponse()));
        given(this.taskService.fetch(any(TaskQueryRequest.class), eq(PageRequest.of(0, 20))))
                .willReturn(tasks);

        this.mvc.perform(get("/projects/1/tasks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":[{\"id\":null,\"title\":null,\"projectId\":null,\"moduleId\":null,\"moduleName\":null,\"start\":null,\"end\":null,\"chargerId\":null,\"chargerName\":null,\"state\":null,\"progress\":null,\"priority\":null}],\"pageable\":\"INSTANCE\",\"last\":true,\"totalPages\":1,\"totalElements\":1,\"size\":1,\"number\":0,\"numberOfElements\":1,\"first\":true,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"empty\":false}"))
                .andDo(MockMvcResultHandlers.print());
    }

}
