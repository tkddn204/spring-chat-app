package com.rightpair.exception;

public class ChatRoomAlreadyExistedException extends BusinessException {
    private final static ErrorCode ERROR_CODE = ErrorCode.CHAT_ROOM_ALREADY_EXISTED_ERROR;

    public ChatRoomAlreadyExistedException() {
        super(ERROR_CODE);
    }
}
