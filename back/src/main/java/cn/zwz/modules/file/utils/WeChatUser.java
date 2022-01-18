package cn.zwz.modules.file.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatUser {
    private String name;
    private String alias;
    private String mobile;
    private String userid;
    private String[] department;
    private String[] order;
    private  String position;
    private  String gender;
    private  String email;
    private  String is_leader_in_dept;
    private  String main_department;
    private List<String> useridlist;
    private String telephone;
}
