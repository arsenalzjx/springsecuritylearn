package com.zjx.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>@ClassName: ValidateCodeController </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/10/9 15:59</p>
 */
@RestController
public class ValidateCodeController {

    static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }
}
