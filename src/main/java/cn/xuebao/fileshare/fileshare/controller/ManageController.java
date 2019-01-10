package cn.xuebao.fileshare.fileshare.controller;
import cn.xuebao.fileshare.fileshare.model.MyFile;
import cn.xuebao.fileshare.fileshare.service.FileService;
import cn.xuebao.fileshare.fileshare.utils.COSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@RestController
public class ManageController {
    @Autowired
    FileService fileService;
    //上传
    @RequestMapping("/upload")
    public void fileUpload(@RequestParam("file") MultipartFile[] multipartFiles, HttpServletRequest request){

        InputStream inputStream=null;
        String filepath=null;
        int uid= (int) request.getSession().getAttribute("uid");
        for (int i=0;i<multipartFiles.length;i++){
            if (multipartFiles[i].getSize()>1024*1024*10){
                return;
            }
            try {
                inputStream=multipartFiles[i].getInputStream();
            }catch (Exception e){

            }
            if (inputStream==null){
                continue;
            }
            String uuid=COSUtil.addFile(inputStream);
            fileService.addFile(uid,multipartFiles[i].getOriginalFilename(),uuid);
        }
    }


    //获取文件列表
    @ResponseBody
    @RequestMapping("/getfilelist")
    public List<MyFile> getFileList(HttpServletRequest request, Integer page){
        int uid= (int) request.getSession().getAttribute("uid");
        List<MyFile> list=fileService.getFileList(uid,page);
        MyFile myFile=null;
        for (int i=0;i<list.size();i++){
            myFile=list.get(i);
            myFile.setFileid(myFile.getId());
            myFile.setFilepath("/downloadFile?id="+myFile.getId());
            myFile.setId((page-1)*8+i+1);
        }
        return list;
    }

    //获取有关文件列表的信息
    @ResponseBody
    @RequestMapping("/getFileCount")
    public List<Integer> getDataAboutList(HttpServletRequest request){
        int uid= (int) request.getSession().getAttribute("uid");
        int count=fileService.getFileCount(uid);
        int pageCount=count/8;
        if (count%8!=0){
            pageCount++;
        }
        List<Integer> list=new LinkedList();
        list.add(count);
        list.add(pageCount);
        return list;
    }

    //下载文件
    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,int id){
        int uid= (int) request.getSession().getAttribute("uid");
        //获取真实的下载链接
        MyFile myFile=fileService.getFile(uid,id);
        String url=COSUtil.getFile(myFile.getUuid());
        File file=new File(url);
        FileInputStream fis = null; //文件输入流
        BufferedInputStream bis = null;
        OutputStream os = null; //输出

        byte[] buffer = new byte[1024];
        try {
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int len;
            while((len=bis.read(buffer))!=-1){
                os.write(buffer,0,len);
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }finally {
            try {
                os.close();
                fis.close();
                bis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            file.delete();
        }
        myFile=fileService.getFile(uid,id);
        COSUtil.deleteFile(myFile.getFilename());
        fileService.deleteFile(uid,id);
        fileService.addFileCount();
        return;
    }

//    @RequestMapping("/deleteFile")
//    public void deleteFile(int id,HttpServletRequest request){
//        int uid= (int) request.getSession().getAttribute("uid");
//        fileService.deleteFile(uid,id);
//        MyFile myFile=fileService.getFile(uid,id);
//        COSUtil.deleteFile(myFile.getFilename());
//    }
//

}
