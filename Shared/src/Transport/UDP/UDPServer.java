package Transport.UDP;

import Contracts.ICustomerService;
import Data.Account;
import Data.Loan;
import Data.ServerPorts;
import Services.SessionService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class UDPServer {
    private static ICustomerService _customerService;

    public UDPServer(ICustomerService customerService) {
        _customerService = customerService;
    }

    public void startServer() {
        System.out.println(String.format("UDP Server is starting"));


        DatagramSocket aSocket = null;
        try {

            //Setup the socket
            int serverPort = ServerPorts.getUDPPort(SessionService.getInstance().getBank());
            aSocket = new DatagramSocket(serverPort);
            byte[] buffer = new byte[1000];

            //Setup the loop to process request
            while (true) {
                System.err.println(String.format("UDP Server STARTED"));

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                System.err.println(String.format("UDP Server received Message"));

                byte[] message = request.getData();
                byte[] answer = processMessage(message);
                int answerPort = request.getPort();

                DatagramPacket reply = new DatagramPacket(
                        answer,
                        answer.length,
                        request.getAddress(),
                        answerPort
                );
                aSocket.send(reply);

                System.err.println(String.format("UDP Server Answered Message on port: %d", answerPort));
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }

    }

    private static byte[] processMessage(byte[] message) throws IOException {

        long currentLoanAmount = 0;
        Serializer loanMessageSerializer = new Serializer<GetLoanMessage>();
        Serializer loanSerializer = new Serializer<Loan>();

        try {

            GetLoanMessage loanMessage = (GetLoanMessage) loanMessageSerializer.deserialize(message);

            Account account = _customerService.getAccount(loanMessage.getFirstName(), loanMessage.getLastName());
            List<Loan> loans = _customerService.getLoans(account.getAccountNumber());

            currentLoanAmount = loans
                    .stream()
                    .mapToLong(l -> l.getAmount())
                    .sum();

            System.err.println(String.format("Loan amount %d", currentLoanAmount));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        byte[] serializedLoan = loanSerializer.serialize(currentLoanAmount);
        return serializedLoan;
    }
}
