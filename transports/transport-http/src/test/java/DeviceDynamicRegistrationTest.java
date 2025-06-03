import cn.devicelinks.common.DynamicRegistrationMethod;
import cn.devicelinks.component.web.utils.SignUtils;
import cn.devicelinks.transport.http.TransportHttpApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 恒宇少年
 * @since 1.0
 */
@SpringBootTest(classes = TransportHttpApplication.class)
@AutoConfigureMockMvc
public class DeviceDynamicRegistrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void provisionKeyRegistrationValid() throws Exception {
        String provisionKey = "K44sQSU3iWU3LJTjFd1hqUDdRUb9zP";
        String provisionSecret = "k7eo7WZedu1fHJSvwxtdzfOa0xY18S0v89TE0VKfexVMlqtAen";
        String timestamp = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("registrationMethod", DynamicRegistrationMethod.ProvisionKey.toString());
        multiValueMap.add("deviceName", "test0004");
        multiValueMap.add("timestamp", timestamp);
        multiValueMap.add("provisionKey", provisionKey);

        String sign = SignUtils.sign(provisionSecret, timestamp, multiValueMap);
        multiValueMap.add("sign", sign);
        MvcResult mvcResult = mockMvc.perform(post("/authenticate/dynamic-registration")
                        .params(multiValueMap)
                ).andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void productKeyRegistrationValid() throws Exception {
        String productKey = "c3437de5102237cbe5e817ded939d2fb";
        String productSecret = "484124b53824f1b43a8726ca76bb0b825b26f9e60a92a3c6ab73d3aeb5c7161e";
        String timestamp = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("registrationMethod", DynamicRegistrationMethod.ProductKey.toString());
        multiValueMap.add("deviceName", "test0004");
        multiValueMap.add("timestamp", timestamp);
        multiValueMap.add("productKey", productKey);

        String sign = SignUtils.sign(productSecret, timestamp, multiValueMap);
        multiValueMap.add("sign", sign);
        MvcResult mvcResult = mockMvc.perform(post("/authenticate/dynamic-registration")
                        .params(multiValueMap)
                ).andExpect(status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
