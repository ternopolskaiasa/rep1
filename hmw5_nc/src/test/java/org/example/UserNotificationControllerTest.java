package org.example;

import org.example.controller.UserNotificationController;
import org.example.model.UserOperationEvent;
import org.example.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserNotificationControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserNotificationController userNotificationController;

    @Test
    public void testSendManualNotification() throws Exception {
        MockitoAnnotations.openMocks(this);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(userNotificationController).build();

        UserOperationEvent event = new UserOperationEvent("user123", "deleted", "user@example.com");
        String payload = "{\"userId\": \"user123\", \"operationType\": \"deleted\", \"email\": \"user@example.com\"}";

        mvc.perform(post("/send-notification")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist()) // ожидается пустой ответ
                .andReturn();

        verify(emailService).sendNotificationEmail(event);
    }
}
