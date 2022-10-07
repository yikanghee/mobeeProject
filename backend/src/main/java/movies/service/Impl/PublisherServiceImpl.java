package movies.service.Impl;

import movies.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 해당 메소드는 채널 topic과 메시지를 받아서
     */
    public void publish(ChannelTopic topic, ChatMessage message){
        /** 채널 topic에 메시지 보내기 **/
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}