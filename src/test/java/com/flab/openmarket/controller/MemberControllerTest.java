package com.flab.openmarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.openmarket.dto.MemberCreateRequest;
import com.flab.openmarket.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @WithMockUser
    @DisplayName("올바른 입력정보로 회원가입을 한다.")
    @Test
    void signup() throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member1@test.com", "memberPasswd!",
                "membername", "01011111111", "customer");

        // when
        mockMvc.perform(
            post("/api/v1/users/signup")
            .with(csrf())
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
        .andExpect(jsonPath("$.message").value("CREATED"))
        .andExpect(jsonPath("$.data").exists());
    }

    @WithMockUser
    @DisplayName("이메일이 null일 경우, 회원가입시 BAD_REQUEST가 응답된다.")
    @Test
    void signupWithEmptyEmail() throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest(null, "memberPasswd!",
                "membername", "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"test@", "test!test.com", "11111"})
    @WithMockUser
    @DisplayName("올바르지 않은 이메일 형식으로 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidEmail(String email) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest(email, "memberPasswd!", "membername",
                "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {
            "@a.c",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@naver.com"
    })
    @WithMockUser
    @DisplayName("이메일의 길이가 최소 5자에서 최대 80자가 아니면 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidEmailLength(String email) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest(email, "memberPasswd!",
                "membername", "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @WithMockUser
    @DisplayName("비밀번호가 null일 경우, 회원가입시 BAD_REQUEST가 응답된다.")
    @Test
    void signupWithEmptyPasswd() throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", null,
                "membername", "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"12345678", "abcdefghi", "!@#%@#$@#@$"})
    @WithMockUser
    @DisplayName("비밀번호에 영문자, 숫자, 특수문자 중 2가지 이상이 포함되어있지 않다면, 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidPasswdPattern(String passwd) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", passwd,
                "membername", "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"1234!", "123456789123456789!!@avasd"})
    @WithMockUser
    @DisplayName("비밀번호의 길이가 최소 8자에서 최대 20자가 아니면 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidPasswdLength(String passwd) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", passwd,
                "membername", "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @WithMockUser
    @DisplayName("이름이 null일 경우, 회원가입시 BAD_REQUEST가 응답된다.")
    @Test
    void signupWithEmptyName() throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                null, "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"123name1", "membername!", "이름1"})
    @WithMockUser
    @DisplayName("이름이 문자(영어, 한글)로만 이뤄지지 않았다면, 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidNamePattern(String name) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                name, "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"n", "daasdqweasdasdqweasddaasdqweasdasdqweasdaa"})
    @WithMockUser
    @DisplayName("이름의 길이가 최소 2자에서 최대 40자가 아니면, 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidNameLength(String name) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                name, "01011111111", "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @WithMockUser
    @DisplayName("핸드폰이 null일 경우, 회원가입시 BAD_REQUEST가 응답된다.")
    @Test
    void signupWithEmptyPhone() throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                "membername", null, "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"010-1234-1251", "phone1234", "010!54123@123"})
    @WithMockUser
    @DisplayName("핸드폰 번호가 숫자로만 이뤄지지 않았다면, 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidPhonePattern(String phone) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                "membername", phone, "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"0101234", "123456789123456789123456789"})
    @WithMockUser
    @DisplayName("핸드폰 번호가 최소 10자에서 최대 20자가 아니면, 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidPhoneLength(String phone) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                "membername", phone, "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").exists());
    }

    @WithMockUser
    @DisplayName("권한이 null일 경우, 회원가입시 BAD_REQUEST가 응답된다.")
    @Test
    void signupWithEmptyRole() throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                "membername", "01012345678", null);

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").exists());
    }

    @ValueSource(strings = {"casd", "123d", "sell"})
    @WithMockUser
    @DisplayName("권한이 대소문자 관계없이 customer/seller가 아니라면, 회원가입시 BAD_REQUEST가 응답된다.")
    @ParameterizedTest
    void signupWithInvalidRolePattern(String role) throws Exception {
        // given
        MemberCreateRequest request = this.createMemberCreateRequest("member@test.com", "password!",
                "membername", role, "customer");

        // when
        mockMvc.perform(
                        post("/api/v1/users/signup")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").exists());
    }


    private MemberCreateRequest createMemberCreateRequest(String email, String passwd, String name, String phone,
                                                          String role
    ) {
        return MemberCreateRequest.builder()
                .email(email)
                .passwd(passwd)
                .name(name)
                .phone(phone)
                .role(role)
                .build();
    }
}