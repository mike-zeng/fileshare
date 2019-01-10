package cn.xuebao.fileshare.fileshare.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyFile {
    int id;
    int fileid;
    public String filename;
    public String filepath;
    public String uuid;
    public int uid;
    public Date time;
}
