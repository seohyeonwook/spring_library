package com.study.library.exception;

// 예외 처리와 관련된 클래스가 위치하는 곳입니다. 예외 상황에 대응하기 위한 코드가 들어 있습니다.
public class SaveException extends RuntimeException{
// RuntimeException 예외의 최상위 클래스
    public SaveException() {
        super("데이터 저장 오류."); //super가 부모로 보낸다
    }
}
// 코드는 SaveException이라는 사용자 정의 예외 클래스를 정의하고 있습니다. 이 클래스는 RuntimeException을 상속받아 구현되었습니다.
//
//  클래스의 생성자는 매개변수를 받지 않는 기본 생성자로, 예외 메시지를 설정합니다.
// 디폴트 생성자는 리턴 값이 없는 예외적인 생성자
// 생성자 내부에서 super("데이터 저장 오류.")를 호출하여 부모 클래스인 RuntimeException의 생성자를 호출하고, 메시지로 "데이터 저장 오류."를 전달합니다.
//
//즉, SaveException은 데이터 저장 시 발생할 수 있는 예외를 나타내며, 이 예외가 발생하면 "데이터 저장 오류."라는 메시지가 함께 출력됩니다.