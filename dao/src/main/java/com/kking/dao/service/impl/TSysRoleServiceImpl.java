package com.kking.dao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kking.dao.entity.TSysRole;
import com.kking.dao.entity.TSysRolePerm;
import com.kking.dao.mapper.TSysRoleMapper;
import com.kking.dao.mapper.TSysRolePermMapper;
import com.kking.dao.service.TSysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TSysRoleServiceImpl implements TSysRoleService {
    @Autowired
    TSysRoleMapper tSysRoleMapper;
    @Autowired
    TSysRolePermMapper tSysRolePermMapper;

    @Override
    public TSysRole selectById(Integer id) {
        return tSysRoleMapper.selectById(id);
    }

    @Override
    public List<TSysRole> selectList(TSysRole tSysRole) {
        return tSysRoleMapper.selectList(tSysRole);
    }

    @Override
    public TSysRole selectOneByProperty(String key, Object value) {
        return tSysRoleMapper.selectOneByProperty(key,value);
    }

    @Override
    public List<TSysRole> selectListByProperty(String key, Object value) {
        return tSysRoleMapper.selectListByProperty(key,value);
    }

    @Override
    public int insert(TSysRole tSysRole) {
        return tSysRoleMapper.insert(tSysRole);
    }

    @Override
    public int deleteById(Integer id) {
        return tSysRoleMapper.deleteById(id);
    }

    @Override
    public int update(TSysRole tSysRole) {
        return tSysRoleMapper.update(tSysRole);
    }

    @Override
    public List<TSysRole> getUserRoleInfo(Integer userId,String permType){
        return tSysRoleMapper.getUserRoleInfo(userId,permType);
    }

    @Override
    @Transactional
    public boolean editPermission(JSONObject json) {
        Integer id = json.getInteger("id");
        TSysRole role = tSysRoleMapper.selectById(id);
        if(role == null){
            throw new RuntimeException("角色不存在");
        }
        JSONArray permList = json.getJSONArray("permList");
        for(int i = 0; i < permList.size(); i++){
            JSONObject perm = permList.getJSONObject(i);
            Integer permId = perm.getInteger("permId");
            Boolean isNew = perm.getBoolean("new");
            if(isNew){
                TSysRolePerm rolePerm = new TSysRolePerm();
                rolePerm.setPermId(permId);
                rolePerm.setRoleId(id);
                tSysRolePermMapper.insert(rolePerm);
            }else{
                TSysRolePerm rolePermCond = new TSysRolePerm();
                rolePermCond.setPermId(permId);
                rolePermCond.setRoleId(id);
                tSysRolePermMapper.delete(rolePermCond);
            }
        }
        return true;
    }


}
