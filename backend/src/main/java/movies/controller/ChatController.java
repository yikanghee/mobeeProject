package movies.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import movies.config.JwtTokenProvider;
import movies.domain.ChatMessage;
import movies.domain.ChatRoom;
import movies.dto.ChatMessageDTO;
import movies.repository.ChatRepository;
import movies.service.PublisherService;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final PublisherService redisPublisher;
    private final ChatRepository chatRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * @MessageMapping("url") : "url"으로 들어오는 메시지 매핑할때 사용하는 애노테이션
     * "/pub/chat/message" 으로 들어오는 message를 ChatMessage으로 바인딩하여 실행
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) throws Exception {

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {

            /** 1. 방 번호로 채팅방을 생성 **/
            chatRepository.enterChatRoom(message.getRoomId());
            /** 2. 객체에 입장한다는 메세지 담기 **/
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        if (message.getMessage() != null) {

            ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
            chatMessageDTO.setKey(message.getRoomId());
            chatMessageDTO.setMessage(message.getMessage());
            chatMessageDTO.setName(message.getSender());

            chatRepository.insertChat(chatMessageDTO);


            chatMessageDTO = null;

            System.out.println("chatMessageDTO = " + chatMessageDTO);
        }

        /** 1. 생성된 방 목록에서 roomId에 해당하는 방 가져와서 **/
        ChannelTopic topic = chatRepository.getTopic(message.getRoomId());

        /** 2. 해당 방에 message 전달하기 **/
        redisPublisher.publish(topic, message);
    }
}
