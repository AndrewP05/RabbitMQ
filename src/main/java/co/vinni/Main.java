package co.vinni;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static co.vinni.Producer.*;

public class Main {
    public static void main(String[] args) {
        System.out.print("Initiated Producer");
        Scanner sc = new Scanner(System.in);
        String msg = "";
        while(msg.isEmpty()) {
            System.out.print("Enter the virtual Host  N (No V host): ");
            String vHost = sc.nextLine();
            if (vHost.isEmpty() || vHost.equalsIgnoreCase("N")) {
                vHost = null;
            }
            System.out.print("Enter the queue  : ");
            String queue = sc.nextLine();
            System.out.print("Enter the topic  N (No topic): : ");
            String topic = sc.nextLine();
            String exchangeName = "";
            if (topic.isEmpty() || topic.equalsIgnoreCase("N")) {
                topic = null;
            }else{
                System.out.print("Enter the exchange Name ");
                exchangeName = sc.nextLine();
            }
            System.out.print("Enter the fanout  N (No fanout): : ");
            String fanout = sc.nextLine();

            System.out.print("Enter the message to be sent : ");
            msg = sc.nextLine();
            if (msg.isEmpty()) {
                System.out.println(" The message is empty");
            } else {
                try {
                    if (topic!= null && !topic.equalsIgnoreCase("N")) {
                        sendMsgTopic(msg, vHost,exchangeName, topic );
                    }if (fanout!= null && !fanout.equalsIgnoreCase("N")) {
                        sendMsgFanout(msg, vHost,fanout );
                    }else{
                        sendMsg(msg, queue, vHost);
                    }

                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
