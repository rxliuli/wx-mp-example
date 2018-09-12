package com.rxliuli.example.wxmpexample.api;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 微信公众号临时媒体资源管理
 *
 * @author rxliuli
 */
@RestController
@RequestMapping("/wx/media/temp/")
public class WxMpMediaTempApi extends WxMpBaseApi {
    /**
     * 上传一个临时文件
     *
     * @param file 临时文件
     * @return 上传到微信服务器后得到的 url
     */
    @PostMapping("upload/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try (final InputStream inputStream = file.getInputStream()) {
            WxMediaUploadResult res = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.FILE, WxConsts.MediaFileType.FILE, inputStream);
            return res.getMediaId();
        } catch (IOException | WxErrorException e) {
            log.info(e.getMessage(), e);
            throw new RuntimeException("上传临时文件发生了某些异常");
        }
    }

    /**
     * 上传一张图片
     *
     * @param file 要上传的文件
     * @return 上传到微信服务器后得到的 url
     */
    @PostMapping("upload/img")
    public String uploadImg(@RequestParam("file") MultipartFile file) {
        try (final InputStream inputStream = file.getInputStream()) {
            final WxMediaUploadResult result = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, WxConsts.MediaFileType.IMAGE, inputStream);
            return result.getMediaId();
        } catch (IOException | WxErrorException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
