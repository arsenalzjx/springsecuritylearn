package com.zjx.web.controller;

import com.zjx.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * <p>@ClassName: FileController </p>
 * <p>@Description: </p>
 * <p>@Author: zjx</p>
 * <p>@Date: 2018/9/28 15:08</p>
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping
    public FileInfo upload(MultipartFile file) throws Exception {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        //保存文件的本地地址
        String folder = "E:\\Workspace\\intellj_idea\\springsecuritylearn\\zjx-security-demo\\src\\main\\java\\com\\zjx\\web\\controller";
        File localFile = new File(folder, new Date().getTime() + ".txt");
        //保存到本地
        file.transferTo(localFile);
        //如果保存到远端可通过获取输入流方式,再存储
        //file.getInputStream();
        return new FileInfo(localFile.getAbsolutePath());
    }

    @PostMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String folder = "E:\\Workspace\\intellj_idea\\springsecuritylearn\\zjx-security-demo\\src\\main\\java\\com\\zjx\\web\\controller";

        try (
                InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
                OutputStream outputStream = response.getOutputStream();
        ) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
