package com.zjx.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * <p>@ClassName: MockServer </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/1 16:23</p>
 */
public class MockServer {
    public static void main(String[] args) throws IOException {
        WireMock.configureFor(8060);
        //清空之前所有配置
        WireMock.removeAllMappings();
        mock("/order/1","01");
        mock("/order/2","02");

    }

    /**
     * @Author: zjx
     * @Description: 抽取配置方法
     * @Date 17:02 2018/10/1
     * @Param [url, file]
     * @return void
     **/
    private static void mock(String url, String file) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/"+file+".txt");
        //根据resource读取txt中内容
        String content =StringUtils.join(FileUtils.readLines(resource.getFile(),"UTF-8").toArray(),"\n");
        //伪造相关url的服务
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                .willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }
}
