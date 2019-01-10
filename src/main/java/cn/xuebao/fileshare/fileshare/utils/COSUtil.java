package cn.xuebao.fileshare.fileshare.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public class COSUtil {
    private static final String secretId="AKIDHWcgptGqmstxXFg0zUuPrRGFCD6ssfgi";
    private static final String secretKey="HcccYgls2Eew7tdQxNk3KcIA1iJza1XR";
    private static final String region_name="ap-guangzhou";
    private static final String bucketName="xuebaofile-1257009269";

    private static COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
    private static ClientConfig clientConfig = new ClientConfig(new Region(region_name));
    private static COSClient cosClient = new COSClient(cred, clientConfig);


    //上传文件
    public static String addFile(InputStream inputStream){
        String key=UUID.randomUUID().toString();
        PutObjectRequest objectRequest=new PutObjectRequest(bucketName,key,inputStream,new ObjectMetadata());
        try {
            PutObjectResult objectResult=cosClient.putObject(objectRequest);
        }catch (Exception e){
            return null;
        }finally {
            try {
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return key;
    }

    public static String getFile(String key){

        UUID uuid=UUID.randomUUID();
        File downFile = new File(uuid.toString());
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);

        cosClient.getObject(getObjectRequest,downFile);

        return uuid.toString();
    }

    public static void deleteFile(String key){
        cosClient.deleteObject(bucketName,key);
    }
}
