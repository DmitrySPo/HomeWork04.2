import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UrlReader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите URL ресурса: ");
            String urlString = scanner.nextLine();

            try {
                readContent(urlString);
                break; // Если всё прошло успешно, выходим из цикла
            } catch (IOException e) {
                System.err.println("Ошибка: введён неправильный URL или нет доступа до ресурса. Попробуйте ещё раз.");
            }
        }

        scanner.close();
    }

    public static void readContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("\\A"); // Читаем весь контент сразу
            if (scanner.hasNext()) {
                String content = scanner.next();
                System.out.println(content);
            } else {
                System.out.println("Не удалось получить содержимое ресурса.");
            }
        } finally {
            connection.disconnect();
        }
    }
}