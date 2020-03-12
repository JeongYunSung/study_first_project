package com.yunseong.first_project.controller;

import com.google.common.net.HttpHeaders;
import com.yunseong.first_project.config.RestDocConfig;
import com.yunseong.first_project.domain.Member;
import com.yunseong.first_project.domain.Team;
import com.yunseong.first_project.repository.MemberRepository;
import com.yunseong.first_project.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocConfig.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void getMember() throws Exception{
        //given
        Team t = new Team("teamname");
        this.em.persist(t);
        Member m = new Member("username", "nickname", this.passwordEncoder.encode("password"), t);
        this.em.persist(m);
        ResultActions oauth = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic("myApp", "pass"))
                .param("username", "username")
                .param("password", "password")
                .param("grant_type", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

        System.out.println("===================================");

        String contentAsString = oauth.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser jackson2JsonParser = new Jackson2JsonParser();
        String access_token = jackson2JsonParser.parseMap(contentAsString).get("access_token").toString();
        //when
        ResultActions resultActions = this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/members")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + access_token)
                .param("size", "10")
                .param("page", "0"));
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("member-list",
/*                        pathParameters(
                                parameterWithName("id").description("회원고유번호")
                        ),*/
                        responseFields(
                                subsectionWithPath("_embedded.memberDtoList").description("회원목록"),
/*                                fieldWithPath("_links.first.href").type(JsonFieldType.STRING).description("첫번째페이지"),
                                fieldWithPath("_links.first.prev").type(JsonFieldType.STRING).description("이전페이지"),*/
                                subsectionWithPath("_links").description("연관페이지"),
/*                                fieldWithPath("_links.first.next").type(JsonFieldType.STRING).description("다음페이지"),
                                fieldWithPath("_links.first.last").type(JsonFieldType.STRING).description("마지막페이지"),*/
                                fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("페이지크기"),
                                fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("총회원수"),
                                fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("전체페이지"),
                                fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("현재페이지")
                        ),
                        requestParameters(
                                parameterWithName("page").description("현재페이지"),
                                parameterWithName("size").description("회원수")
                        )));
    }
}