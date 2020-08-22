package org.cab.admin.component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.lang3.StringUtils;
import org.cab.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class OssComponent {
    private static List<String> imgType = Arrays.asList("image/gif", "image/png", "image/jpeg",
            "image/webp", "image/bmp");
    @Autowired
    private OSS oss;

    @Autowired
    private SnowFlakeComponent snowFlakeComponent;
    private static final String DIR_PATH = "cab/image/";
    @Value("${ali.oss.endpoint}")
    private String endpoint;
    @Value("${ali.oss.bucketName}")
    private String bucketName;
    @Value("${ali.oss.schema}")
    private String schema;
    @Value("${img.size}")
    private int size;


    /**
     * 图片上传
     *
     * @param bucketName oss bucketName
     * @param img        上传的文件
     * @param dirPath    上传至oss 文件夹路径 null为默认路径
     * @return
     * @throws IOException
     */
    public String uploadImg(MultipartFile img, @Nullable String bucketName, @Nullable String dirPath)
            throws
            IOException {
        //校验图片格式是否正确 内容是否为空  图片大小是否大于限定
        if (!imgType.contains(img.getContentType()) || ImageIO.read(img.getInputStream()) ==
                null || !checkImgSize(img.getSize())) {
            throw new CustomException("图片不合法");
        }
        //判断用户是否有传入bucketName与 文件夹路径 没有传入使用默认的
        String finalDirPath = dirPath == null ? DIR_PATH : dirPath;
        String finalBucketName = bucketName == null ? this.bucketName : bucketName;
        //获取图片后缀
        String suffix = StringUtils.substring(img.getOriginalFilename(), img.getOriginalFilename
                ().lastIndexOf("."));
        //命名新的名称
        String fileName = snowFlakeComponent.getId() + suffix;
        // 设置请求头  防止图片无预览 直接下载
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");
        //上传至OSS
        try {
            oss.putObject(finalBucketName, finalDirPath + fileName, new ByteArrayInputStream(img.getBytes()), meta);
        } finally {
            //关闭连接
            oss.shutdown();
        }
        //返回访问url <Schema>://<Bucket>.<外网Endpoint>/<Oss文件访问路径>
        StringBuilder retUrl = new StringBuilder();
        retUrl.append(schema).append("://").append(finalBucketName).append(".").append(endpoint)
                .append("/")
                .append(finalDirPath).append(fileName);
        return retUrl.toString();
    }

    /**
     * 方法重载
     * @param img
     * @return
     * @throws IOException
     */
    public String uploadImg(MultipartFile img) throws IOException {
        return uploadImg(img, null);
    }

    /**
     * 方法重载
     * @param img
     * @param bucketName
     * @return
     * @throws IOException
     */
    public String uploadImg(MultipartFile img, String bucketName) throws IOException {
        return uploadImg(img, bucketName, null);
    }

    /**
     * 判断文件大小
     *
     * @param len 文件长度  参数非法
     *            限制单位（K）
     * @return 超过限制大小 返回 false
     */
    private boolean checkImgSize(Long len) {
        return (double) len / 1024 > this.size ? false : true;
    }
}
