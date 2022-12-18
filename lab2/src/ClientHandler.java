import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        InputStream is = null;

        try {
            System.out.println("processing new client from " + clientSocket.getInetAddress());
            // поток для получения данных от клиента
            is = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Scanner scanner = new Scanner(System.in);
            OutputStream os = clientSocket.getOutputStream();

            // поток для отправления клиенту ответа

            boolean workStatus = true;
            while (workStatus) {
                System.out.println("1. read question from client");

                //1. read question from client
                String question = reader.readLine();
                System.out.println(question);

                String response = "";
                if (question.equals("exit")) {
                    response = "До свидания!";
                    workStatus = false;
                } else if (question.equals("Как дела?")) {
                    String[] resps = {"Хорошо :)", "Плохо :(", "Отлично! :)))"};
                    response = resps[(int) (Math.random() * resps.length)];
                } else if (question.equals("Сколко тебе лет?")) {
                    String[] resps = {"18", "20", "Секрет)"};
                    response = resps[(int) (Math.random() * resps.length)];
                } else if (question.equals("Откуда ты?")) {
                    String[] resps = {"Я из Турция.", "Я из России.", "Я из Марс."};
                    response = resps[(int) (Math.random() * resps.length)];
                } else {
                    response = "Не понял вопроса!";
                }

                // 2. send response to client
                DataOutputStream serverOutput = new DataOutputStream(os);
                serverOutput.write((response + "\n").getBytes("UTF-8"));
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
