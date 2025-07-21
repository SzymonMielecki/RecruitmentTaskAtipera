package org.szymonmielecki.recruitmenttaskatipera;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class RecruitmentTaskAtiperaApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenRepos_whenGetRepositories_thenSuccess() throws Exception {
        mvc.perform(get("/?username=octocat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].owner_login").value("octocat"))
                .andExpect(jsonPath("$[0].repository_name").isString())
                .andExpect(jsonPath("$[0].branches").isArray())
                .andExpect(jsonPath("$[0].branches[0].branch_name").isString())
                .andExpect(jsonPath("$[0].branches[0].last_commit_hash").isString());
    }
}
