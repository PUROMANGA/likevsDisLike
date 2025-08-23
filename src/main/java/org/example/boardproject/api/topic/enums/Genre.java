package org.example.boardproject.api.topic.enums;

import lombok.Getter;

@Getter
public enum Genre {
    // 문학/출판
    NOVEL,          // 소설
    WEB_NOVEL,      // 웹소설
    ESSAY,          // 에세이
    MAGAZINE,       // 잡지
    COMIC,          // 만화
    WEBTOON,        // 웹툰

    // 영상
    MOVIE,          // 영화
    DRAMA,          // 드라마
    ANIMATION,      // 애니메이션
    DOCUMENTARY,    // 다큐멘터리
    VARIETY_SHOW,   // 예능

    // 음악
    SONG,           // 노래
    ALBUM,          // 앨범

    // 게임/인터랙티브
    GAME,           // 게임
    BOARD_GAME,     // 보드게임
    ESPORTS,        // e스포츠

    // 공연/연극
    MUSICAL,        // 뮤지컬
    THEATER,        // 연극
    CONCERT,        // 콘서트
    FESTIVAL,       // 페스티벌

    // 기타 미디어
    YOUTUBE_CONTENT; // 유튜브/스트리밍 콘텐츠

    public static Genre checkGenre(String genre) {
        for(Genre g : Genre.values()) {
            if(g.name().equalsIgnoreCase(genre)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Genre " + genre + " not found");
    }
}
