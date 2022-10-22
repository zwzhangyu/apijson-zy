package com.example.maynard.tradition.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.maynard.tradition.entity.Message;
import com.example.maynard.tradition.entity.Server;
import com.example.maynard.tradition.entity.User;
import com.example.maynard.tradition.mapper.UserMapper;
import com.example.maynard.tradition.mapper.MessageMapper;
import com.example.maynard.tradition.mapper.ServerMapper;
import com.example.maynard.tradition.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 *     服务器业务逻辑实现类
 * </pre>
 */
@Service
public class ServerServiceImpl implements ServerService {

    @Autowired(required = false)
    private ServerMapper serverMapper;

    @Autowired(required = false)
    private MessageMapper messageMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public void saveByServer(Server server) {
        if (server != null && server.getId() != null) {
            serverMapper.updateById(server);
        } else {
            serverMapper.insert(server);
        }
    }

    @Override
    public Server findByServerId(Long serverId) {
        return serverMapper.selectById(serverId);
    }


    @Override
    public void removeServer(Long serverId) {
        serverMapper.deleteById(serverId);
    }

    @Override
    public Page<Server> findAll(String queryType, String queryKeywords, Page<Server> page) {
        return page.setRecords(serverMapper.findAll(queryType, queryKeywords, page));
    }

    @Override
    public void batchRemove(List<Long> ids) {
        serverMapper.deleteBatchIds(ids);
    }

    @Override
    public void correctServerStatus() {
        //更服务器释放时间<当前时间 && 状态为 used的为 free
        //1.发自动释放通知
        Long now = System.currentTimeMillis();
        List<Server> servers = serverMapper.findOuttime(now);
        for (int i = 0; i < servers.size(); i++) {
            Server server = servers.get(i);
            User user = userMapper.findByUserName(server.getApplyUserName());
            if(user != null) {
                Message message = new Message();
                message.setContent("您为【"+server.getProjectName()+"】申请的服务器已到期，已自动释放！");
                message.setStatus("unread");
                message.setUserId(user.getUserId());
                message.setCreateTime(now);
                messageMapper.insert(message);
            }
        }

        //2.释放
        serverMapper.releaseOuttime(now);

        //3.时间到了，将服务器应状态改成使用中
        serverMapper.applyOuttime(now);
    }

    @Override
    public Server findByProjectName(String projectName) {
        Server server = new Server();
        server.setProjectName(projectName);
        return serverMapper.selectOne(server);
    }

}
