//package movies.config.Handler;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import movies.Utils.RoomUtil;
//import movies.domain.ChatMessage;
//import movies.repository.ChatRepository;
//import movies.service.ChatService;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class StompHandler implements ChannelInterceptor {
//
//    private final ChatService chatService;
//    private final ChatRepository chatRepository;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        ChatMessage chatMessage = null;
//
//        if (StompCommand.CONNECT == accessor.getCommand()) {
//            log.info("CONNECT 성공!");
//
//        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
//
//            log.info("SUBSCRIBE 성공!");
//
//            String sessionId = (String) message.getHeaders().get("simpSessionId");
//            String roomId = chatService.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
//
//            RoomUtil.setRoomIdMap(sessionId, roomId);
//
//            log.info(roomId);
//            log.info(sessionId);
//
//
//            if (RoomUtil.hasRoom(roomId) == 0) {
//                RoomUtil.setNewRoomInRoomCountMap(roomId);
//                RoomUtil.upRoomCount(roomId);
//            } else {
//                RoomUtil.upRoomCount(roomId);
//            }
//
//        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
//            log.info("DISCONNECT 성공!");
//            String sessionId = (String) message.getHeaders().get("simpSessionId");
//            log.info(sessionId);
//
//            String roomId = RoomUtil.getRoomIdMap(sessionId);
//
//            int memberCount = RoomUtil.getRoomMemberCount(roomId);
//
//            if (memberCount > 0) {
//                RoomUtil.downRoomCount(roomId);
//            }
//        }
//        return message;
//    }
//}
