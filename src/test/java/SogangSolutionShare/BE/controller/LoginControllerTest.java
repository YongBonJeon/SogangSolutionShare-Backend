package SogangSolutionShare.BE.controller;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired private MockMvc mockMvc;
    private MockHttpSession session;

    @Test
    public void 회원가입_로그인_로그아웃() throws Exception {
        session = new MockHttpSession();
        // 회원가입
        mockMvc.perform(post("/api/join")
                        .contentType("application/form-data")
                        .param("loginId", "tester1")
                        .param("password", "tester1")
                        .param("name", "tester1")
                        .param("email", "tester1@naver.com")
                        .param("isAuthorized", "true")
                )
                .andExpect(status().isCreated());
        // 로그인
        mockMvc.perform(post("/api/login")
                        .session(session)
                        .contentType("application/form-data")
                        .param("loginId", "tester1")
                        .param("password", "tester1"))
                .andExpect(status().isOk());

        Object loginMember = session.getAttribute("loginMember");
        Assertions.assertThat(loginMember).isNotNull();

        // 로그아웃
        mockMvc.perform(post("/api/logout")
                        .session(session))
                .andExpect(status().isOk());

        Assertions.assertThat(session.isInvalid()).isTrue();
    }

}