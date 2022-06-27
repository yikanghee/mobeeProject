package movies.Utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RoomUtil {

    private static Map<String, Integer> roomCountMap = new HashMap<>();
    private static Map<String, String> roomIdMap = new HashMap<>();

    public static int hasRoom(String roomKey) {
        try {
            int i = roomCountMap.get(roomKey);
            log.info("roomCount : " + i);

            return 1;
        } catch (Exception exception) {
            log.info("not has Room with RoomKey");
            return 0;
        }
    }

    public static int getRoomMemberCount(String roomKey) {
        return Integer.parseInt(String.valueOf(roomCountMap.get(roomKey)));
    }

    public static int setNewRoomInRoomCountMap(String roomKey)  {
        roomCountMap.put(roomKey, 0);

        return roomCountMap.get(roomKey);
    }

    public static String setRoomIdMap(String sessionId, String roomId) {
        roomIdMap.put(sessionId, roomId);

        return roomIdMap.get(roomId);
    }

    public static String getRoomIdMap(String sessionId) {
        return (String.valueOf(roomIdMap.get((sessionId))));
    }

    public static void upRoomCount(String roomKey)  {
        int roomValue = roomCountMap.get(roomKey);
        roomCountMap.put(roomKey, roomValue + 1);
    }

    public static void downRoomCount(String roomKey)  {
        int roomValue = roomCountMap.get(roomKey);
        roomCountMap.put(roomKey, roomValue - 1);
    }

}
