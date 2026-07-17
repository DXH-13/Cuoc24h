package com.cuoc24h.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.web.FilterChainProxy;

@SpringBootTest
class ApiIntegrationTest {

    @Autowired private WebApplicationContext context;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private MockMvc mvc() {
        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(context)
                    .addFilters(springSecurityFilterChain)
                    .build();
        }
        return mockMvc;
    }

    @Test
    void createBooking_valid_returns201WithPublicCode() throws Exception {
        String body = """
                {"customerName":"Nguyen Van A","customerPhone":"0900000001",
                 "pickupAddress":"Quan 1","dropoffAddress":"San bay",
                 "pickupTime":"2026-07-20T10:30:00","vehicleType":"4_SEATS","note":"x"}
                """;
        mvc().perform(post("/api/bookings").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.publicCode", notNullValue()))
                .andExpect(jsonPath("$.data.status", is("NEW")));
    }

    @Test
    void createBooking_invalid_returns400WithFieldErrors() throws Exception {
        mvc().perform(post("/api/bookings").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(org.hamcrest.Matchers.greaterThan(0))));
    }

    @Test
    void driverRegister_valid_returnsPending() throws Exception {
        String body = """
                {"fullName":"Tran Van B","phone":"0911111111","operatingArea":"TP.HCM",
                 "vehicleType":"7_SEATS","licensePlate":"51A-12345","seatCount":7,
                 "serviceType":"TAXI_SERVICE"}
                """;
        mvc().perform(post("/api/drivers/register").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.status", is("PENDING")));
    }

    @Test
    void adminEndpoint_withoutToken_returns401() throws Exception {
        mvc().perform(get("/api/admin/bookings")).andExpect(status().isUnauthorized());
    }

    @Test
    void adminLogin_wrongPassword_returns401() throws Exception {
        String body = "{\"username\":\"admin\",\"password\":\"nope\"}";
        mvc().perform(post("/api/admin/auth/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminLogin_thenAccessProtectedAndTransition_works() throws Exception {
        // 1) login
        String loginBody = "{\"username\":\"admin\",\"password\":\"cuoc24h\"}";
        MvcResult login = mvc().perform(post("/api/admin/auth/login")
                        .contentType(MediaType.APPLICATION_JSON).content(loginBody))
                .andExpect(status().isOk())
                .andReturn();
        String token = readData(login).get("accessToken").asText();
        String auth = "Bearer " + token;

        // 2) create a booking to operate on
        String bookingBody = """
                {"customerName":"Le Thi C","customerPhone":"0900000002",
                 "pickupAddress":"Quan 3","dropoffAddress":"Quan 7","vehicleType":"4_SEATS"}
                """;
        mvc().perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON).content(bookingBody))
                .andExpect(status().isCreated());
        long bookingId = newestBookingId(auth);

        // 3) /me works with token
        mvc().perform(get("/api/admin/auth/me").header("Authorization", auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is("admin")));

        // 4) valid transition NEW -> CONTACTING
        mvc().perform(patch("/api/admin/bookings/" + bookingId + "/status")
                        .header("Authorization", auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"CONTACTING\",\"internalNote\":\"Da goi khach\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status", is("CONTACTING")));

        // 5) invalid transition CONTACTING -> NEW returns 400
        mvc().perform(patch("/api/admin/bookings/" + bookingId + "/status")
                        .header("Authorization", auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"NEW\"}"))
                .andExpect(status().isBadRequest());

        // 6) a notification log was created for the booking (SENT no-op in tests)
        mvc().perform(get("/api/admin/bookings/" + bookingId + "/notifications")
                        .header("Authorization", auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(org.hamcrest.Matchers.greaterThanOrEqualTo(1))));
    }

    private JsonNode readData(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString()).get("data");
    }

    /** The public create response hides the id; look it up via the admin list (newest first). */
    private long newestBookingId(String auth) throws Exception {
        MvcResult list = mvc().perform(get("/api/admin/bookings?page=0&size=1")
                        .header("Authorization", auth))
                .andReturn();
        return readData(list).get("items").get(0).get("id").asLong();
    }
}
