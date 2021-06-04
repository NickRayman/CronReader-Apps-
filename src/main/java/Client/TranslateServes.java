package Client;

import Common.Cron;

import java.io.*;
import java.net.Socket;


public class TranslateServes {
    public CronHuman translateCroneMessage(Cron cron){
        CronHuman cronHuman = null;

        /**
         * Этой строкой мы запрашиваем у сервера доступ на соединение
         */
        try(Socket clientSocket = new Socket("127.0.0.1", 8080)){
            cronHuman = runWithText(clientSocket, cron);
        }
        catch (IOException | ClassNotFoundException e){
            System.out.println("Объект не передался");
        }
        return cronHuman;
    }

    /**
     *Метод отправляет на сервер объект Cron и получает объект CronHuman, потом возвращает его
     */
    public CronHuman runWithText(Socket clientSocket, Cron cron) throws IOException, ClassNotFoundException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            System.out.println("Клиент заработал");

            objectOutputStream.writeObject(cron);
            System.out.println("Клиент отправил объект");
            objectOutputStream.flush();

            CronHuman cronFromServer = (CronHuman) objectInputStream.readObject();
            System.out.println("Клиент получил объект");
            return cronFromServer;

            }
    }
}
