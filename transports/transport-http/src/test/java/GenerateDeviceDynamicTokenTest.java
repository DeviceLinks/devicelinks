import cn.devicelinks.api.device.center.DeviceFeignClient;
import cn.devicelinks.common.utils.HmacSignatureAlgorithm;
import cn.devicelinks.component.web.api.ApiResponseUnwrapper;
import cn.devicelinks.component.web.utils.SignUtils;
import cn.devicelinks.transport.http.TransportHttpApplication;
import org.junit.jupiter.api.Test;
import org.minbox.framework.util.JsonUtils;
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
 * 生成设备动态令牌测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@SpringBootTest(classes = TransportHttpApplication.class)
@AutoConfigureMockMvc
public class GenerateDeviceDynamicTokenTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DeviceFeignClient deviceFeignClient;

    @Test
    public void testGenerateDeviceDynamicToken() throws Exception {
        String deviceId = "683eb1ee57c3f0986247c1c8";
        String deviceSecret = ApiResponseUnwrapper.unwrap(deviceFeignClient.decryptDeviceSecret(deviceId));
        String timestamp = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("deviceId", deviceId);
        parameters.add("deviceName", "test0003");
        parameters.add("timestamp", timestamp);
        parameters.add("signAlgorithm", HmacSignatureAlgorithm.HmacSHA1.toString());
        parameters.add("sign", SignUtils.sign(HmacSignatureAlgorithm.HmacSHA1, deviceSecret, timestamp, parameters));
        MvcResult mvcResult = mockMvc.perform(
                        post("/authenticate/dynamic-token-credentials")
                                .params(parameters))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(JsonUtils.beautifyJson(mvcResult.getResponse().getContentAsString()));
    }
}
