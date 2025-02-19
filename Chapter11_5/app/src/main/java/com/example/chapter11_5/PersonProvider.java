package com.example.chapter11_5;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PersonProvider extends ContentProvider { // 내용 제공자 클래스

    private static final String AUTHORITY = "com.example.chapter11_5";
    // AUTHORITY는 보통 패키지명으로 작성한다. 그래야 다른 앱과 중복될 가능성이 낮기 때문이다.
    private static final String BASE_PATH = "person";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    // content URI 예시 : content://com.example.chapter11_5/person/1
    // content:// : 내용 제공자에 의해 제어되는 데이터라는 의미.
    // AUTHORITY : 특정 내용 제공자를 구분하는 고유한 값
    // BASE PATH : 요청할 데이터의 자료형을 결정함 (여기서는 테이블 이름)
    // ID : 맨 뒤 숫자. 요청할 데이터 레코드를 지정함.

    private static final int PERSONS = 1;
    private static final int PERSON_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); // UriMatcher : URI를 매칭함.
    static{
        uriMatcher.addURI(AUTHORITY, BASE_PATH, PERSONS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH+"/#", PERSON_ID);
        // addURI 메서드를 이용해 추가된 URI 중에서 실행 가능한 것이 있는지 확인함.
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    // query() : 내용 제공자를 이용해 값을 조회하고 싶을 때 사용하는 메서드
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        // 1번째 파라미터 : URI
        // 2번째 파라미터 : 어떤 칼럼들을 조회할 것인지 지정 (null이면 모두 조회)
        // 3번째 파라미터 : SQL에서 where 절에 들어갈 조건을 지정 (null이면 조건 없음)
        // 4번째 파라미터 : 3번째 파라미터에 값이 있을 경우, 그 안에 들어갈 조건 값을 대체하기 위해 사용됨.
        // 5번째 파라미터 : 정렬 칼럼 (null이면 정렬 적용 X)

        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                cursor = database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.ALL_COLUMNS,
                        s, null, null, null, DatabaseHelper.PERSON_NAME + " ASC"); // ASC 오름차순
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI "+ uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        // 액티비티에서 getContentReslover 메서드를 쓰면 ContentResolver 객체를 반환함.
        return cursor;
    }

    @Nullable
    @Override
    // getType() : MIME 타입이 무엇인지 알고 싶을 때 사용하는 메서드.
    public String getType(@NonNull Uri uri) {
        switch(uriMatcher.match(uri)){
            case PERSONS:
                return "vnd.android.cursor.dir/persons"; // 결과값으로 MIME 타입이 반환됨. (알 수 없는 경우 null 값 반환)
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri); // Uri 값이 유효하지 않은 경우 예외 발생
        }
    }

    @Nullable
    @Override
    // insert() : 내용 제공자를 이용해 값을 추가하고 싶을 때 사용하는 메서드.
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        // 1번째 파라미터 : URI
        // 2번째 파라미터 : 저장할 칼럼명과 값이 들어간 ContentValues 객체

        long id = database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        if(id > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            // notifyChange() : 레코드가 추가, 수정, 삭제되었을 때 변경이 일어났음을 알려주는 역할을 함.
            return _uri; // 새로 추가된 값의 URI 정보가 반환됨.
        }
        throw new SQLException("추가 실패 -> URI : " + uri);
    }

    @Override
    // delete() : 내용 제공자를 이용해 값을 삭제하고 싶을 때 사용하는 메서드.
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        // 1번째 파라미터 : URI
        // 2번째 파라미터 : SQL에서 where 절에 들어갈 조건을 지정 (null이면 조건 없음)
        // 3번째 파라미터 : 2번째 파라미터에 값이 있을 경우, 그 안에 들어갈 조건 값을 대체하기 위해 사용됨.

        int count = 0;
        switch(uriMatcher.match(uri)){
            case PERSONS:
                count = database.delete(DatabaseHelper.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count; // 영향을 받은 레코드의 개수가 반환됨.
    }

    @Override
    // update() : 내용 제공자를 이용해 값을 수정하고 싶을 때 사용하는 메서드.
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        // 1번째 파라미터 : URI
        // 2번째 파라미터 : 저장할 칼럼명과 값들이 들어간 ContentValues 객체
        // 3번째 파라미터 : SQL에서 where 절에 들어갈 조건을 지정 (null이면 조건 없음)
        // 4번째 파라미터 : 3번째 파라미터에 값이 있을 경우, 그 안에 들어갈 조건 값을 대체하기 위해 사용됨.

        int count = 0;
        switch(uriMatcher.match(uri)){
            case PERSONS:
                count = database.update(DatabaseHelper.TABLE_NAME, contentValues, s, strings); // delete 메서드랑 여기만 다름
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count; // 영향을 받은 레코드의 개수가 반환됨.
    }

}
