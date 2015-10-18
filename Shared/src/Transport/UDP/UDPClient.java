package Transport.UDP;

import java.io.*;
import java.net.*;

public class UDPClient implements Closeable {

    private DatagramSocket _socket;
    private InetAddress _host;
    private static final int TIME_OUT = 20000;


    public UDPClient() {

        try {
            _host = InetAddress.getByName("localhost");
            _socket = new DatagramSocket();
            _socket.setSoTimeout(TIME_OUT);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public byte[] sendMessage(byte[] message, int serverPort) throws IOException {


        DatagramPacket request = new DatagramPacket(message, message.length, _host, serverPort);
        _socket.send(request);

//        _socket.getPort()
        System.err.println(String.format("UDP CLIENT is waiting answer on port: %d", _socket.getLocalPort()));


        byte[] buffer = new byte[1000];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

        _socket.receive(reply);
        System.err.println(String.format("UDP CLIENT RECEIVED ANSWER!"));


        return reply.getData();
    }

    @Override
    public void close() throws IOException {
            if (_socket != null) {
                _socket.close();
            }
    }
}
