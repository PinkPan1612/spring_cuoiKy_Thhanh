package com.example.demo.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        // don't save file
        if (file.isEmpty()) {
            return "";
        }
        // relative path: absolute Path
        String rootPath = System.getProperty("user.dir") + "/src/main/resources/uploads";

        String finalName = "";
        try {
            byte[] bytes = file.getBytes();
            // tìm nơi lưu file(),
            // sparator là dấu /
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists()) // kiểm tra tồn tại
                dir.mkdirs(); // nếu ko tồn tại thì tạo mới(make directory)

            // tạo mới tên file = thời gian hiện tại + tên file gốc
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return finalName;
    }
}
