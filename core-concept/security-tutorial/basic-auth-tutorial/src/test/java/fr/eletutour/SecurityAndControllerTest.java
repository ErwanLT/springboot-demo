package fr.eletutour;

import fr.eletutour.configuration.SecurityConfig;
import fr.eletutour.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@WebMvcTest(HelloController.class)
@Import(SecurityConfig.class)
class SecurityAndControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Test de l'accès non authentifié à /goodbye (doit réussir)
    @Test
    void shouldAllowUnauthenticatedAccessToGoodbye() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/goodbye"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("GoodBye world!"));
    }

    // Test de l'accès non authentifié à /hello (doit échouer)
    @Test
    void shouldDenyUnauthenticatedAccessToHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // Test de l'accès à /hello avec un utilisateur standard
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldAllowUserAccessToHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello world!"));
    }

    // Test de l'accès à /hello avec un admin
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldAllowAdminAccessToHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello world!"));
    }

    // Test de l'accès à /goodbye avec un utilisateur authentifié
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void shouldAllowAuthenticatedAccessToGoodbye() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/goodbye"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("GoodBye world!"));
    }

    // Test avec de mauvaises informations d'identification
    @Test
    void shouldDenyAccessWithWrongCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .with(httpBasic("user", "wrongpassword")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // Test avec les bonnes informations d'identification
    @Test
    void shouldAllowAccessWithCorrectCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                .with(httpBasic("user", "password")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello world!"));
    }
}