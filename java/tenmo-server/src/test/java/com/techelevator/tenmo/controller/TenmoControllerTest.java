package com.techelevator.tenmo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

@SpringBootTest
@AutoConfigureMockMvc
class TenmoControllerTest {

	@Autowired
	private MockMvc mvc;
	
    @Autowired
    private ObjectMapper mapper;
	
//	@Before
//	public void setUp() {
//		SecurityContextHolder.clearContext();
//	}
    
    @Test
    public void list_transactions_user_shows_transactions_for_user() throws Exception {
    	if(!isControllerSecure()) {
    		fail("Not authenticated controller");
    	}
    	
    	mvc.perform(getTransfers()).andExpect(status().isOk());
    	mvc.perform(getOne("/transfers/1")).andExpect(status().isOk());
    }
    
//    @Test
//    	void just_fucking_work_for_fucks_sake() throws Exception {
//    		User user = new User((long)4, "doggo", "password", "true");
//    	
//    		mvc.perform(get("/user/4"))
//    		.contentType(MediaType.APPLICATION_JSON)
//    		.content(mapper.writeValueAsString(user))
//    		.andExpect(status().isOk());
//    		
//    		User userEntity = user.getUsername("doggo");
//    		assertEquals(user.getAuthorities().equals("true"));
//    }

//	@Test
//	public void six_dollars_from_1_to_2_sendTransfer() throws Exception {
//		
//		if(!isControllerSecure()) {
//			fail("Authentication & Authorization not enabled for TenmoController.create()");
//		}
//		
//		Transfers sendTransfer = new Transfers();
//				sendTransfer.setTransferTypeId((long) 2);
//				sendTransfer.setTransferStatusId((long) 2);
//				sendTransfer.setAccountFrom((long)1);
//				sendTransfer.setAccountTo((long) 2);
//				sendTransfer.setAmount(null);
//		
//		final String userToken = getTokenForLogin("user", "password", mvc);
//		
//		mvc.perform(post("/transfers/send")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(toJson(sendTransfer))
//				.header("Authorization", "Bearer" + userToken))
//				.andExpect(status().isCreated());
//	}

    private boolean isControllerSecure() throws InvocationTargetException, IllegalAccessException {
        boolean isControllerSecure = false;
        for (Annotation annotation : TenmoController.class.getAnnotations()) {
            Class<? extends Annotation> type = annotation.annotationType();
            if( type.getName().equals("org.springframework.security.access.prepost.PreAuthorize")) {
                for (Method method : type.getDeclaredMethods()) {
                    Object value = method.invoke(annotation, (Object[])null);
                    if(value.equals("isAuthenticated()")) {
                        isControllerSecure = true;
                        break;
                    }
                }
            }
        }
        return isControllerSecure;
    }
    
    private String toJson(Transfers transfer) throws JsonProcessingException {
        return mapper.writeValueAsString(transfer);
    }
}
