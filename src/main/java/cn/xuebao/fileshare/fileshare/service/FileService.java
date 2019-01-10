package cn.xuebao.fileshare.fileshare.service;

import cn.xuebao.fileshare.fileshare.model.MyFile;

import java.util.List;

public interface FileService{
    public boolean addFile(int uid,String filename,String filepath);

    public List<MyFile> getFileList(int id);

    public List<MyFile> getFileList(int id,int page);

    public int getFileCount(int uid);

    public MyFile getFile(int uid,int id);

    public void deleteFile(int uid,int id);

    public void addFileCount();
}
