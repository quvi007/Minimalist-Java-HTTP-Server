import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(80);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            //System.out.println("Connected to the client " + clientSocket.getInetAddress().toString() + " " + clientSocket.getPort());
            BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String rcvdData = "";
            String firstLine = "";
            Boolean firstTime = true;
            while (!(rcvdData = br.readLine()).equals("")) {
                if (firstTime) {
                    firstTime = false;
                    firstLine = rcvdData;
                }
            }
            ByteArrayInputStream bAIS = new ByteArrayInputStream(firstLine.getBytes());
            Scanner scanner = new Scanner(bAIS);
            String rqst = "";
            rqst = scanner.next();
            rqst = scanner.next();

            if (rqst.length() == 1)
                rqst += "index.html";

            String localhost = "C:\\Users\\Quvi\\Desktop\\root";
            StringBuilder fullPath = new StringBuilder(localhost);

            for (char ch : rqst.toCharArray()) {
                fullPath.append((ch == '/') ? '\\' : ch);
            }

            File file = new File(fullPath.toString());
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(fullPath.toString());
                byte[] bytes = fis.readAllBytes();
                fis.close();
                bos.write(bytes);
                bos.flush();
            }
            clientSocket.close();
        }

    }
}
