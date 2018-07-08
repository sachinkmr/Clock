import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;

public class Demo {
    public static void main(String[] args) {
        long n= LocalTime.now().getNano();
        System.out.println(n);
        System.out.println(LocalTime.now());
        System.out.println(LocalTime.now().getSecond()+ n*10^-9);
        System.out.println((double) n/1000000000);

    }
}
