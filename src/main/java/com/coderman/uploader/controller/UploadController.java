package com.coderman.uploader.controller;

import com.coderman.uploader.dto.FileChunkDTO;
import com.coderman.uploader.dto.FileChunkResultDTO;
import com.coderman.uploader.response.RestApiResponse;
import com.coderman.uploader.service.IUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangyukang
 * @Date 2021/1/16 19:22
 * @Version 1.0
 **/
@RestController
public class UploadController {

    @Autowired
    private IUploadService uploadService;

    private Logger logger= LoggerFactory.getLogger(UploadController.class);

    /**
     * 检查分片是否存在
     * @return
     */
    @GetMapping(value = "/upload/chunk")
    public RestApiResponse<Object> checkChunkExist(FileChunkDTO chunkDTO, HttpServletResponse response) {
        FileChunkResultDTO fileChunkCheckDTO;
        try {
            fileChunkCheckDTO = uploadService.checkChunkExist(chunkDTO);
            return RestApiResponse.success( fileChunkCheckDTO);
        } catch (Exception e) {
            Map<String, Object> error = getErrorMap(response, e);
            logger.error("check chunk exist error :{}",e.getMessage());
            return RestApiResponse.error(error);
        }
    }


    /**
     * 上传文件分片
     * @param chunkDTO
     * @return
     */
    @PostMapping(value = "/upload/chunk")
    public RestApiResponse<Object> uploadChunk(FileChunkDTO chunkDTO,HttpServletResponse response)  {
        try {
            uploadService.uploadChunk(chunkDTO);
            return RestApiResponse.success(chunkDTO.getIdentifier());
        } catch (Exception e) {
            Map<String, Object> error = getErrorMap(response, e);
            logger.error("upload chunk error :{}",e.getMessage());
            return RestApiResponse.error(error);
        }
    }

    /**
     * 设置上传错误响应
     * @param response
     * @param e
     * @return
     */
    private Map<String, Object> getErrorMap(HttpServletResponse response, Exception e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Map<String, Object> error = new HashMap<>();
        error.put("errorMsg", e.getMessage());
        error.put("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return error;
    }

}
