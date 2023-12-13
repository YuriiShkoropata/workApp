import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static StringBuilder buildKRS = new StringBuilder();
    static String nip = "0000000000";
    static ArrayList<String> listOfNIPs = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        readFromFile();

        for (int i = 0; i < listOfNIPs.size(); i++) {
            nip = listOfNIPs.get(i);
            String url = "https://krs-pobierz.pl/szukaj?q=" + nip;

            readFromWeb(url);
        }

        PrintWriter zapis = new PrintWriter("C:\\Users\\pobie\\Desktop\\krs.txt");
        zapis.println(buildKRS);
        zapis.close();
    }


    public static void readFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\pobie\\Desktop\\nip.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            listOfNIPs.add(line);
        }
        reader.close();
    }


    public static void readFromWeb(String webURL) throws IOException {
        URL url = new URL(webURL);
        InputStream is = url.openStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = br.readLine()) != null) {

                Matcher matcher = Pattern.compile("KRS\\s[0-9]{10}").matcher(line);
                if (matcher.find()) {
                    String krs = matcher.group();
                    System.out.println(krs);
                    buildKRS.append("NIP:" + nip + "   ");
                    buildKRS.append(krs);
                    buildKRS.append("\n");
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("URL is malformed!!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
}
