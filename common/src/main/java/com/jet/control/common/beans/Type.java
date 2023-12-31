package com.jet.control.common.beans;

/**
 * Для идентификации сообщений, которые отправляются.
 * Типы сообщений представлены в этом enum-классе
 *
 */
public enum Type {
    // Состояние самолета или аэропорт
    STATE,
    // Маршрут самолета
    ROUTE,
    // Инциализация, маршрут принят в обработку
    START
}
