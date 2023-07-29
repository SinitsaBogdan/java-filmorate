package ru.yandex.practicum.filmorete.enums;

public enum EnumStatusFriend {

    STATUS_FRIENDS__NOT_CONFIRMED("Не в друзьях"),
    STATUS_FRIENDS__NO_RESPONSE("Не подтвержденная заявка"),
    STATUS_FRIENDS__CONFIRMED("В друзьях");

    final String status;

    EnumStatusFriend(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
