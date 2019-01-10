package cn.xuebao.fileshare.fileshare.service.serviceimpl;

import cn.xuebao.fileshare.fileshare.mapper.FileMapper;
import cn.xuebao.fileshare.fileshare.model.MyFile;
import cn.xuebao.fileshare.fileshare.service.FileService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;
    @Override
    public boolean addFile(int uid, String filename, String uuid) {
        MyFile myFile=new MyFile();
        myFile.setUid(uid);
        myFile.setFilename(filename);
        myFile.setUuid(uuid);
        return fileMapper.addFile(myFile);
    }

    @Override
    public List<MyFile> getFileList(int id) {
        return fileMapper.getFileList(id);
    }

    @Override
    public List<MyFile> getFileList(int id, int page) {
        PageHelper.startPage(page,8);
        List<MyFile> list=fileMapper.getFileList(id);
        return list;
    }

    @Override
    public int getFileCount(int uid) {
        return fileMapper.getFileCount(uid);
    }

    @Override
    public MyFile getFile(int uid,int id) {
        return fileMapper.getFile(uid,id);
    }

    @Override
    public void deleteFile(int uid, int id) {
        fileMapper.deleteFile(uid,id);
    }

    @Override
    public void addFileCount() {
        fileMapper.addFileCount();
    }
}
