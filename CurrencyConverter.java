import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CurrencyConverter {
    private static final String API_KEY = "c9656059ecf01837163bebb9";
    private static final String BASE_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static double getExchangeRate(String fromCurrency, String toCurrency) {
        try {
            URL url = new URL(BASE_URL + fromCurrency);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
            return jsonResponse.getAsJsonObject("rates").get(toCurrency).getAsDouble();
        } catch (Exception e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSea bienvenido/a al Conversor de Moneda");
            System.out.println("1) Dólar a Peso Argentino");
            System.out.println("2) Peso Argentino a Dólar");
            System.out.println("3) Dólar a Real Brasileño");
            System.out.println("4) Real Brasileño a Dólar");
            System.out.println("5) Dólar a Peso Colombiano");
            System.out.println("6) Peso Colombiano a Dólar");
            System.out.println("7) Salir");
            System.out.println("Elija una opción válida: ");

            int option = scanner.nextInt();
            if (option == 7) {
                System.out.println("Gracias por usar el conversor de moneda.");
                break;
            }

            String fromCurrency = "", toCurrency = "";
            switch (option) {
                case 1: fromCurrency = "USD"; toCurrency = "ARS"; break;
                case 2: fromCurrency = "ARS"; toCurrency = "USD"; break;
                case 3: fromCurrency = "USD"; toCurrency = "BRL"; break;
                case 4: fromCurrency = "BRL"; toCurrency = "USD"; break;
                case 5: fromCurrency = "USD"; toCurrency = "COP"; break;
                case 6: fromCurrency = "COP"; toCurrency = "USD"; break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    continue;
            }

            System.out.print("Ingrese la cantidad a convertir: ");
            double amount = scanner.nextDouble();

            double rate = getExchangeRate(fromCurrency, toCurrency);
            if (rate != -1) {
                System.out.println(amount + " " + fromCurrency + " equivale a " + String.format("%.2f", amount * rate) + " " + toCurrency);
            }
        }
        scanner.close();
    }
}
