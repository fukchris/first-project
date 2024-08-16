package BaseBall;
//숫자야구 게임설명
//1-9까지 서로다른 수로 이루어진 3자리 수를 맞추는게임
//힌트 제공  같은수가 같은자리면  스트라이크
// 같은수에 다른자리면 볼 같은수가 전혀없으면 낫씽
//힌트를 이용해 3자리수를 맞추면승리
//게임 순서
//1.컴퓨터가 1부터9까지 임의의 수 3개를 선택한다
//2.게임 플레이어는 생각하는 임의의 숫자 3개를 입력한다
//3. 컴퓨터는입력한숫자의 결과를 출력한다
//4.2-3번의 과정을 반복하여 3개의 숫자를 모두 맞히면 게임이 종료된다
//5.게임을 종료한후 게임을 다시시작하거나 종료할수있다 재시작:1 종료:2
//예외처리
//플레이어가 잘못된 숫자를 입력했을때 IllegalArgumentException 을 발생시킨후 어플리케이션은 종료된다
//기능 목룍
//컴퓨터의 숫자생성
//  [] "숫자야구게임을 시작합니다 "를 출력한다
//  [] 1부터 9까지의 숫자세개를 생성한다
//플레이어의 숫자입력
//  [] "숫자를 입력해주세요"를 출력한다
//  [] 숫자를 입력받는다
//  [] 빈 문자가 아님을 검증한다
//  [] 길이가 3인 문자열 임을 검증한다
//  [] 숫자로 이루어진 문자열 임을 검증한다
//  [] 숫자가 1이상,9이하 임을 검증한다
//  [] 서로다른숫자임을 검증한다
//힌트생성
//컴퓨터에 저장한숫자와 입력된 숫자를 비교한다
//스트라이크와 볼 개수를 계산하여 반환한다
//힌트 출력
// " 1 스트라이크 1볼 "과 같이 스트라이크 개수와 볼개수에 대한 힌트를 출력한다
//게임 재시작및 종료
//스트라이크 3개 일시 게임종료
//"3개의 숫자를 모두 맞히셨습니다 " 게임종료 \n "게임을 새로 시작혀려면 1, 종료하려면 2 를 입력하세요" 출력
//[]입력에 따라 게임을 재시작 또는 종료한다


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        //스캐너 (사용자의 입력을 받기위한 도구)
        Scanner scanner = new Scanner(System.in);
        //랜덤 숫자를 만들기위한 유틸클래스 랜덤 함수
        Random random = new Random();
        // ??불린 사실,거짓을 판단하기위한 변수
        boolean gameRunning = true;
        //while 반복문 게임이 참일 경우 계속돌아간다
        while (gameRunning) {
            //??리스트  대상숫자를 리스트 배열로 만든다
            //generateTargetNumbers 메소드의 인수로 랜덤 을 보내고있다
            List<Integer> targetNumbers = generateTargetNumbers(random);
            System.out.println("숫자 야구 게임을 시작합니다.");
         //??시도 횟수의 초기값
            int attempts = 0;
         // 무한반복문 loop
            while (true) {
                //트라이 캐치문 예외발생시 캐치로이동해 에러메세지 출력
                //적절한 에러 관리를 위한구문
                try {
                    //??사용자로부터 입력받은 값을 설정하는 입력처리.스캐너 사용자가직접입력하는 숫자
                    List<Integer> userNumbers = getUserInput(scanner);
                    //??userNumber 를 다시 validateUserInput 에 넣고있다
                    // 사용자 입력값이 예상 외의 값이라 처리 불가능한 경우를 위해 유효성검사를 하고 있는 것으로 보임
                    validateUserInput(userNumbers);
                    //스트라이크와 볼의 숫자를 기록한다.타겟넘버는 위에서 설정한 랜덤한 숫자의배열
                    int strikeCnt = countStrikes(targetNumbers, userNumbers);
                    int ballCnt = countBalls(targetNumbers, userNumbers);
                    // 결과를 판단해서 콘솔창에 출력
                    if (strikeCnt > 0 && ballCnt > 0) {
                        System.out.println("볼 : " + ballCnt + ", 스트라이크 : " + strikeCnt);
                    // 위 if 가 false 일경우 ,else if 가 true 일때 출력
                    } else if (ballCnt > 0) {
                        System.out.println("볼 : " + ballCnt);
                    //위의 if가 false 일경우,else if 가 true 일때출력
                    } else if (strikeCnt > 0) {
                        System.out.println("스트라이크 : " + strikeCnt);
                    //위의 if가 false 일때 출력
                    } else {
                        System.out.println("낫싱");
                    }
                    // 스트라이크가 3일경우 브레이크걸어서 게임종료 3이 아니면 65라인으로 돌아가서 반복
                    if (strikeCnt == 3) {
                        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                        break;
                    }
                     // ??다음회차로 이동하는경우 후위증감연산자로 1을 더한다 .
                    attempts++;
                    //??68라인 유효성검사에서 에러가나면 게임을 종료한다
                } catch (IllegalArgumentException e) {
                    gameRunning = false;
                    break;
                }
            }
            // 사용자의 입력값을 받아 다시시작 또는 게임종료
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            int choice = scanner.nextInt();

            if (choice == 2) {
                System.out.println("게임을 종료합니다.");
                break;
            }
            else if(choice == 1){
                System.out.println("게임을 다시시작합니다");
                continue;
            }

        }

        scanner.close();
    }

    // ?? 무작위로 서로 다른 세 자리 숫자 생성
    private static List<Integer> generateTargetNumbers(Random random) {
        List<Integer> numbers = new ArrayList<>();
        while (numbers.size() < 3) {
            int num = 1 + random.nextInt(9);
            if (!numbers.contains(num)) {
                numbers.add(num);
            }
        }
        return numbers;
    }

    // ?? 사용자로부터 입력을 받아 List<Integer>로 반환
    private static List<Integer> getUserInput(Scanner scanner) {
        System.out.print("숫자를 입력해주세요 : ");
        int userInput = scanner.nextInt();
        List<Integer> userNumbers = new ArrayList<>();
        userNumbers.add(userInput / 100);
        userNumbers.add((userInput / 10) % 10);
        userNumbers.add(userInput % 10);
        return userNumbers;
    }

    // ??사용자 입력 유효성 검사
    private static void validateUserInput(List<Integer> userNumbers) {
        if (userNumbers.size() != 3 ||
                userNumbers.stream().anyMatch(n -> n < 1 || n > 9) ||
                userNumbers.stream().distinct().count() < 3) {
            throw new IllegalArgumentException();
        }
    }

    // 스트라이크 개수 계산
    private static int countStrikes(List<Integer> target, List<Integer> user) {
        int strikeCnt = 0;
        for (int i = 0; i < 3; i++) {
            if (target.get(i).equals(user.get(i))) {
                strikeCnt++;
            }
        }
        return strikeCnt;
    }

    // 볼 개수 계산
    private static int countBalls(List<Integer> target, List<Integer> user) {
        int ballCnt = 0;
        for (int i = 0; i < 3; i++) {
            if (!target.get(i).equals(user.get(i)) && target.contains(user.get(i))) {
                ballCnt++;
            }
        }
        return ballCnt;
    }

}
