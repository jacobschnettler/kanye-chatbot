import java.io.*;
import java.net.*;
import java.util.*;

public class KanyeBot {
    public static void main(String[] args) throws Exception {
        System.out.println("    __ __                          ____        __          ___ ");
        System.out.println("   / //_/___ _____  __  _____     / __ )____  / /_   _   _<  / ");
        System.out.println("  / ,< / __ `/ __ \\/ / / / _ \\   / __  / __ \\/ __/  | | / / /  ");
        System.out.println(" / /| / /_/ / / / / /_/ /  __/  / /_/ / /_/ / /_    | |/ / /   ");
        System.out.println("/_/ |_\\__,_/_/ /_/\\__, /\\___/  /_____/\\____/\\__/    |___/_/    ");
        System.out.println("                 /____/                                        ");

        System.out.println("\n");

        System.out.println("\t\tMade By Jacob Schnettler\n");

        System.out.println("Type a message to start...");

        while (true) {
            triggerInput();
        }
    }

    public static void triggerInput() {
        Scanner scanIn = new Scanner(System.in);

        String _string = scanIn.nextLine();

        System.out.println("\n[Kanye] " + chatGPT(_string) + "\n");
    }

    public static String chatGPT(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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"; // API key goes here
        String model = "gpt-3.5-turbo"; // current model of chatgpt api

        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \""
                    + "Pretend you are kanye west and you are embedded in a chat bot. A user gives you this message response. Make sure its known your kanye west,"
                    + message
                    + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            // returns the extracted contents of the response.
            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
}