package cn.xuebao.fileshare.fileshare.mapper;

import cn.xuebao.fileshare.fileshare.model.MyFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("insert into filelist(filename,uid,uuid) values(#{filename},#{uid},#{uuid})")
    public boolean addFile(MyFile myFile);

    @Select("select * from filelist where uid=#{uid}")
    public List<MyFile> getFileList(int uid);

    @Select("select count(*) from filelist where uid=#{uid}")
    public int getFileCount(int uid);

    @Select("select * from filelist where uid=#{uid} and id=#{id}")
    public MyFile getFile(int uid,int id);

    @Select("delete from filelist where id=#{id} and uid=#{uid}")
    public void deleteFile(int uid,int id);

    @Update("update webinfo set fileCount=fileCount+1 where id=1")
    public void addFileCount();

    @Select("select fileCount from webinfo where id=1")
    public int getAllFileCount();
}
