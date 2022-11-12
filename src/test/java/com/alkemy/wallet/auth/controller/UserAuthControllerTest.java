package com.alkemy.wallet.auth.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@AutoConfigureMockMvc
class UserAuthControllerTest {

  @Autowired
  private MockMvc mvc;


  public JSONObject credentials() {
    JSONObject credentials = new JSONObject();
    credentials.put("email", "a3@gmail.com");
    credentials.put("password", "asdasdasd");
    return credentials;
  }

  public JSONObject wrongCredentials() {
    JSONObject credentials = new JSONObject();
    credentials.put("email", "a@gmail.com");
    credentials.put("password", "asdasdasd");
    return credentials;
  }

  public JSONObject emptyEmailParameterCredential() {
    JSONObject credentials = new JSONObject();
    credentials.put("email", "");
    credentials.put("password", "asdasdasd");
    return credentials;
  }

  @Test
  public void signInWithValidateCredential() throws Exception {
    JSONObject credentials = credentials();
    mvc.perform(MockMvcRequestBuilders.post("/auth/login").accept(MediaType.APPLICATION_JSON)
            .content(credentials.toString().getBytes()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void signInWithWrongCredential() throws Exception {
    JSONObject credentials = wrongCredentials();
    mvc.perform(MockMvcRequestBuilders.post("/auth/login").accept(MediaType.APPLICATION_JSON)
            .content(credentials.toString().getBytes()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void signInWithEmptyEmailCredential() throws Exception {
    JSONObject credentials = emptyEmailParameterCredential();
    mvc.perform(MockMvcRequestBuilders.post("/auth/login").accept(MediaType.APPLICATION_JSON)
            .content(credentials.toString().getBytes()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }


}