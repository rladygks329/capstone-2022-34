package com.capstone.momomeal.api;

import com.capstone.momomeal.domain.Category;
import com.capstone.momomeal.domain.ChatRoom;
import com.capstone.momomeal.service.ChatRoomService;
import com.capstone.momomeal.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ChatRoomApiController.class)
class ChatRoomApiControllerTest {



}