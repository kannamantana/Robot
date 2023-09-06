import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    final static int ROUT_NUMBER = 1000;

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) {

        Runnable logic = () -> {
            int count = 0;
            String route = generateRoute("RLRFR", 100);
            for (int i = 0; i < route.length(); i++) {
                if (route.charAt(i) == 'R')
                    count++;
            }
            synchronized (sizeToFreq) {
                if (sizeToFreq.containsKey(count)) {
                    sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                } else {
                    sizeToFreq.put(count, 1);
                }
            }
        };
        for (int i = 0; i < ROUT_NUMBER; i++) {
            Thread thread = new Thread(logic);
            thread.start();
        }
        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений - " +
                max.getKey() + " (встретилось " + max.getValue() + " раз)");
        System.out.println("Другие размеры: ");
        for (Map.Entry entry : sizeToFreq.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }
    }
}