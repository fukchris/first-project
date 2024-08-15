package BaseBall;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        // 스캐너(사용자로부터 입력을 받기 위한 부품. 종이로 쓴 글자를 스캔한다는 느낌이라 스캐너)
        // 스캐너 객체에 유저가 입력한 단어가 저장된다
        Scanner scanner = new Scanner(System.in);
        // 랜덤 숫자 등을 만들기 위한 유틸 클래스. 랜덤함수가 따로 있기도 함.
        Random random = new Random();
        // 플래그(트루,폴스), 무언가 판단하기 위한 불리언 변수
        boolean gameRunning = true;

        // 반복문, 게임러닝이 참인 경우 계속 돌아간다. 
        while (gameRunning) {
            // 대상이 되는 숫자를 리스트 배열(인티저가 들어있는)로 만들고 있는 것으로 보인다. 
            // generateTargetNumbers메소드의 인수로 랜덤을 보내고 있다.  
            List<Integer> targetNumbers = generateTargetNumbers(random);
            System.out.println("숫자 야구 게임을 시작합니다.");
            // 시도 횟수의 초기화 값으로 보인다. 
            int attempts = 0;
            // 무한 반복문 
            while (true) {
                // 예외처리. 도중에 에러가 생기면 캐치로 이동해서 에러 메시지를 보낸다.
                // 에러가 일어나는 이유는 변수의 이용이 잘못되어 참조에러가 생겼거나 사용자 임의의 에러가 다양하다. 
                // 에러처리 안한 상태로 에러가 생기면 프로그램이 멈추기 때문에 적절한 에러를 관리하기 위한 구문.
                // 트라이캐치 구문이라고 함
                try {
                    // 사용자로부터 입력 받은 값을 설정하는 입력 처리로 보임. 스캐너가 사용자가 직접 입력하는 숫자일 것. 
                    // getUserInput에서 무언가 사용자 입력값을 처리한 뒤, 그것을 배열로 만들어서 userNumbers 변수에 담고 있다. 
                    List<Integer> userNumbers = getUserInput(scanner);
                    // userNumbers를 다시 validateUserInput에 넣어서 무언가 하고 있다. 
                    // 이름으로 추측컨데 사용자 입력값이 예상 외의 값이라 처리 불가능한 경우를 위해 유효성검사를 하고 있는 것으로 보임
                    validateUserInput(userNumbers);
                    // 스트라이크와 볼의 숫자를 기록한다. 타켓넘버는 위에서 설정한 랜덤한 숫자들의 배열이기 때문에 아마 야구의 성적을 임의로 의미하는 듯 하다.
                    // userNumbers는 사용자가 직접 입력한 숫자의 배열.  둘을 이용해서 스트라이크인기 볼인지 계산 하는 듯 하다. 실제 야구와는 다를 듯
                    int strikeCnt = countStrikes(targetNumbers, userNumbers);
                    int ballCnt = countBalls(targetNumbers, userNumbers);

                    // 게임결과(스트라이크와 볼의 개수 계산 결과)를 판단해서 콘솔창(터미널창)에 결과 출력하는 처리 
                    // 이 부분도 메소드로 따로 뺴서 하는게 좋았을 듯 하다. 
                    if (strikeCnt > 0 && ballCnt > 0) {
                        System.out.println("볼 : " + ballCnt + ", 스트라이크 : " + strikeCnt);
                    } else if (ballCnt > 0) {
                        System.out.println("볼 : " + ballCnt);

                    } else if (strikeCnt > 0) {
                        System.out.println("스트라이크 : " + strikeCnt);

                    } else {
                        System.out.println("낫싱");
                    }
                    // 또 다른 분기문
                    // 스트라이크가 3인경우는 브레이크를 걸어서 게임을 종료한다. 3아 안되면 다시 38번라인으로 돌아가서 반복한다. 야구의 다음 회차를 모방 한듯 
                    if (strikeCnt == 3) {
                        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                        break;
                    }
                    // 다음회차로 이동하는 경우 후위증감연산자로 1을 더한다. 후위 증감이기 때문에 다음번에 출력 시에 1이 증가한다. 전위라면 바로 더해진다. 
                    // 이 더하는 처리의 결과를 바로 시스템인프린트라인으로 출력하지 않는 이상 여기서 큰 차이는 없다 
                    attempts++;
                } catch (IllegalArgumentException e) {
                    // 41번 유효성 검사에서 무언가 에러가 일어나면 게임을 종료한다. 
                    gameRunning = false;
                    break;
                }
            }
            // 스트라이크 3이 일어나면 여기로 온다. 2중 반복문 중 64번쨰 줄에서 브레이크를 했기 때문에 다음 처리는 이곳이 된다. 
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            // 콘솔에서 사용자로부터 입력값을 숫자로 받는다. 
            int choice = scanner.nextInt();

            if (choice != 1) {
                System.out.println("게임을 종료합니다.");
                break;
            }
        }
        // 스캐너는 사용자의 입력값을 추적하기 위한 객체로 메모리를 소모한다. 
        // 반환해준다.
        scanner.close();
    }

    // 무작위로 서로 다른 세 자리 숫자 생성
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

    // 사용자로부터 입력을 받아 List<Integer>로 반환
    private static List<Integer> getUserInput(Scanner scanner) {
        System.out.print("숫자를 입력해주세요 : ");
        int userInput = scanner.nextInt();
        List<Integer> userNumbers = new ArrayList<>();
        userNumbers.add(userInput / 100);
        userNumbers.add((userInput / 10) % 10);
        userNumbers.add(userInput % 10);
        return userNumbers;
    }

    // 사용자 입력 유효성 검사
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
